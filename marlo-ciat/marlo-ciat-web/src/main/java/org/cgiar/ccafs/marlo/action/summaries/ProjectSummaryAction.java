/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/

package org.cgiar.ccafs.marlo.action.summaries;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConfig;
import org.cgiar.ccafs.marlo.config.PentahoListener;
import org.cgiar.ccafs.marlo.data.model.Deliverable;
import org.cgiar.ccafs.marlo.data.model.DeliverableDocument;
import org.cgiar.ccafs.marlo.data.model.DeliverableOutput;
import org.cgiar.ccafs.marlo.data.model.Project;
import org.cgiar.ccafs.marlo.data.model.ProjectPartner;
import org.cgiar.ccafs.marlo.data.model.ResearchCenter;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.data.service.IProjectService;
import org.cgiar.ccafs.marlo.data.service.impl.CenterService;
import org.cgiar.ccafs.marlo.utils.APConstants;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.pentaho.reporting.engine.classic.core.Band;
import org.pentaho.reporting.engine.classic.core.CompoundDataFactory;
import org.pentaho.reporting.engine.classic.core.Element;
import org.pentaho.reporting.engine.classic.core.ItemBand;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.ReportFooter;
import org.pentaho.reporting.engine.classic.core.SubReport;
import org.pentaho.reporting.engine.classic.core.TableDataFactory;
import org.pentaho.reporting.engine.classic.core.modules.output.pageable.pdf.PdfReportUtil;
import org.pentaho.reporting.engine.classic.core.util.TypedTableModel;
import org.pentaho.reporting.libraries.resourceloader.Resource;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrés Felipe Valencia Rivera. CCAFS
 */
public class ProjectSummaryAction extends BaseAction implements Summary {

  private static final long serialVersionUID = 5009625767712161204L;
  private static Logger LOG = LoggerFactory.getLogger(ProjectSummaryAction.class);

  // Streams
  InputStream inputStream;
  // PDF bytes
  private byte[] bytesPDF;
  // Parameters
  private long startTime;
  private ResearchCenter loggedCenter;
  private Project project;
  // Front-end
  private long projectID;
  // Services
  private ICenterService centerService;
  private IProjectService projectService;

  @Inject
  public ProjectSummaryAction(APConfig config, CenterService centerService) {
    super(config);
    this.centerService = centerService;
  }

  @Override
  public String execute() throws Exception {


    ByteArrayOutputStream os = new ByteArrayOutputStream();
    ResourceManager manager =
      (ResourceManager) ServletActionContext.getServletContext().getAttribute(PentahoListener.KEY_NAME);
    // manager.registerDefaults();
    try {

      Resource reportResource =
        manager.createDirectly(this.getClass().getResource("/pentaho/projectSummary.prpt"), MasterReport.class);

      // Get main report
      MasterReport masterReport = (MasterReport) reportResource.getResource();

      // Get program from DB
      // project = projectManager.getProjectById(projectID);


      // Get details band
      ItemBand masteritemBand = masterReport.getItemBand();
      // Create new empty subreport hash map
      HashMap<String, Element> hm = new HashMap<String, Element>();
      // method to get all the subreports in the prpt and store in the HashMap
      this.getAllSubreports(hm, masteritemBand);
      // Uncomment to see which Subreports are detecting the method getAllSubreports
      System.out.println("Pentaho SubReports: " + hm);

      // Set Main_Query
      String masterQueryName = "main";
      CompoundDataFactory cdf = CompoundDataFactory.normalize(masterReport.getDataFactory());
      TableDataFactory sdf = (TableDataFactory) cdf.getDataFactoryForQuery(masterQueryName);
      TypedTableModel model = this.getMasterTableModel();
      sdf.addTable(masterQueryName, model);
      masterReport.setDataFactory(cdf);

      // Start Setting Subreports

      // Subreport Description
      this.fillSubreport((SubReport) hm.get("description"), "description");
      this.fillSubreport((SubReport) hm.get("partners"), "partners");
      this.fillSubreport((SubReport) hm.get("deliverables"), "deliverables");

      PdfReportUtil.createPDF(masterReport, os);
      bytesPDF = os.toByteArray();
      os.close();
    } catch (Exception e) {
      LOG.error("Error generating PDF " + e.getMessage());
      throw e;
    }
    // Calculate time of generation
    long stopTime = System.currentTimeMillis();
    stopTime = stopTime - startTime;
    LOG.info("Downloaded successfully: " + this.getFileName() + ". User: "
      + this.getCurrentUser().getComposedCompleteName() + ". Time to generate: " + stopTime + "ms.");
    return SUCCESS;

  }


  private void fillSubreport(SubReport subReport, String query) {
    CompoundDataFactory cdf = CompoundDataFactory.normalize(subReport.getDataFactory());
    TableDataFactory sdf = (TableDataFactory) cdf.getDataFactoryForQuery(query);
    TypedTableModel model = null;
    switch (query) {
      case "description":
        model = this.getDescriptionTableModel();
        break;
      case "partners":
        model = this.getPartnersTableModel();
        break;
      case "deliverables":
        model = this.getDeliverablesTableModel();
        break;
    }
    sdf.addTable(query, model);
    subReport.setDataFactory(cdf);
  }


  /**
   * Get all subreports and store then in a hash map.
   * If it encounters a band, search subreports in the band
   * 
   * @param hm List to populate with subreports found
   * @param itemBand details section in pentaho
   */
  private void getAllSubreports(HashMap<String, Element> hm, ItemBand itemBand) {
    int elementCount = itemBand.getElementCount();
    for (int i = 0; i < elementCount; i++) {
      Element e = itemBand.getElement(i);
      // verify if the item is a SubReport
      if (e instanceof SubReport) {
        hm.put(e.getName(), e);
        if (((SubReport) e).getElementCount() != 0) {
          this.getAllSubreports(hm, ((SubReport) e).getItemBand());
          // If report footer is not null check for subreports
          if (((SubReport) e).getReportFooter().getElementCount() != 0) {
            this.getFooterSubreports(hm, ((SubReport) e).getReportFooter());
          }
        }
      }
      // If is a band, find the subreport if exist
      if (e instanceof Band) {
        this.getBandSubreports(hm, (Band) e);
      }
    }
  }

  /**
   * Get all subreports in the band.
   * If it encounters a band, search subreports in the band
   * 
   * @param hm
   * @param band
   */
  private void getBandSubreports(HashMap<String, Element> hm, Band band) {
    int elementCount = band.getElementCount();
    for (int i = 0; i < elementCount; i++) {
      Element e = band.getElement(i);
      if (e instanceof SubReport) {
        hm.put(e.getName(), e);
        // If report footer is not null check for subreports
        if (((SubReport) e).getReportFooter().getElementCount() != 0) {
          this.getFooterSubreports(hm, ((SubReport) e).getReportFooter());
        }
      }
      if (e instanceof Band) {
        this.getBandSubreports(hm, (Band) e);
      }
    }
  }

  public byte[] getBytesPDF() {
    return bytesPDF;
  }

  @Override
  public int getContentLength() {
    return bytesPDF.length;
  }

  @Override
  public String getContentType() {
    return "application/pdf";
  }

  private TypedTableModel getDeliverablesTableModel() {
    TypedTableModel model = new TypedTableModel(
      new String[] {"id", "deliverableTitle", "type", "subType", "startDate", "endDate", "crossCutting",
        "deliverableOutputs", "supportingDocuments"},
      new Class[] {Long.class, String.class, String.class, String.class, String.class, String.class, String.class,
        String.class, String.class});

    for (Deliverable deliverable : project.getDeliverables().stream().filter(d -> d.isActive())
      .collect(Collectors.toList())) {
      Long id = deliverable.getId();
      String deliverableTitle = deliverable.getName();
      String type = null;
      String subType = null;
      if (deliverable.getDeliverableType() != null && deliverable.getDeliverableType().getDeliverableType() != null) {
        type = deliverable.getDeliverableType().getName();
        subType = deliverable.getDeliverableType().getDeliverableType().getName();
      }

      SimpleDateFormat formatter = new SimpleDateFormat("MMM yyyy");
      String startDate = null;

      if (deliverable.getStartDate() != null) {
        startDate = formatter.format(deliverable.getStartDate());
      }
      String endDate = null;
      if (deliverable.getEndDate() != null) {
        endDate = formatter.format(deliverable.getEndDate());
      }

      String crossCutting = "";
      if (deliverable.getDeliverableCrosscutingTheme() != null) {

        if (deliverable.getDeliverableCrosscutingTheme().getClimateChange()) {
          crossCutting += "&#9679;  Climate Change <br>";
        }
        if (deliverable.getDeliverableCrosscutingTheme().getGender()) {
          crossCutting += "&#9679;  Gender <br>";
        }
        if (deliverable.getDeliverableCrosscutingTheme().getYouth()) {
          crossCutting += "&#9679;  Youth <br>";
        }
        if (deliverable.getDeliverableCrosscutingTheme().getNa()) {
          crossCutting += "&#9679;  N/A <br>";
        }
        if (deliverable.getDeliverableCrosscutingTheme().getCapacityDevelopment()) {
          crossCutting += "&#9679;  Capacity Development <br>";
        }
        if (deliverable.getDeliverableCrosscutingTheme().getBigData()) {
          crossCutting += "&#9679;  Big Data <br>";
        }
        if (deliverable.getDeliverableCrosscutingTheme().getImpactAssessment()) {
          crossCutting += "&#9679;  Impact Assessment <br>";
        }
        if (deliverable.getDeliverableCrosscutingTheme().getPoliciesInstitutions()) {
          crossCutting += "&#9679;  Policies and Institutions <br>";
        }
      }
      String deliverableOutputs = "";
      for (DeliverableOutput deliverableOutput : deliverable.getDeliverableOutputs().stream()
        .filter(deo -> deo.isActive()).collect(Collectors.toList())) {
        deliverableOutputs += "&#9679;  " + deliverableOutput.getResearchOutput().getId() + " - "
          + deliverableOutput.getResearchOutput().getTitle() + "<br>";
      }
      String supportingDocuments = null;
      for (DeliverableDocument deliverableDocument : deliverable.getDeliverableDocuments().stream()
        .filter(dd -> dd.isActive()).collect(Collectors.toList())) {
        supportingDocuments += "&#9679;  " + deliverableDocument.getLink() + "<br>";
      }

      model.addRow(new Object[] {id, deliverableTitle, type, subType, startDate, endDate, crossCutting,
        deliverableOutputs, supportingDocuments});
    }
    return model;
  }

  private TypedTableModel getDescriptionTableModel() {
    TypedTableModel model = new TypedTableModel(
      new String[] {"title", "startDate", "endDate", "principalInvestigator", "projectContact", "crossCutting",
        "projectOutputs"},
      new Class[] {String.class, String.class, String.class, String.class, String.class, String.class, String.class});

    String title = null;
    if (project.getName() != null && !project.getName().isEmpty()) {
      title = project.getName();
    }

    SimpleDateFormat formatter = new SimpleDateFormat("MMM yyyy");
    String startDate = null;

    if (project.getStartDate() != null) {
      startDate = formatter.format(project.getStartDate());
    }
    String endDate = null;
    if (project.getEndDate() != null) {
      endDate = formatter.format(project.getEndDate());
    }
    String principalInvestigator = null;
    // project.get

    model.addRow(new Object[] {title, startDate, endDate, principalInvestigator});
    return model;
  }

  @SuppressWarnings("unused")
  private File getFile(String fileName) {
    // Get file from resources folder
    ClassLoader classLoader = this.getClass().getClassLoader();
    File file = new File(classLoader.getResource(fileName).getFile());
    return file;
  }

  @Override
  public String getFileName() {
    StringBuffer fileName = new StringBuffer();
    fileName.append("FullProjectReportSummary-CIAT" + "-");
    fileName.append("P" + projectID + "-");
    fileName.append(new SimpleDateFormat("yyyyMMdd-HHmm").format(new Date()));
    fileName.append(".pdf");
    return fileName.toString();
  }

  private void getFooterSubreports(HashMap<String, Element> hm, ReportFooter reportFooter) {

    int elementCount = reportFooter.getElementCount();
    for (int i = 0; i < elementCount; i++) {
      Element e = reportFooter.getElement(i);
      if (e instanceof SubReport) {
        hm.put(e.getName(), e);
        if (((SubReport) e).getElementCount() != 0) {
          this.getAllSubreports(hm, ((SubReport) e).getItemBand());

        }
      }
      if (e instanceof Band) {
        this.getBandSubreports(hm, (Band) e);
      }
    }
  }

  @Override
  public InputStream getInputStream() {
    if (inputStream == null) {
      inputStream = new ByteArrayInputStream(bytesPDF);
    }
    return inputStream;
  }

  /**
   * Get the main information of the report
   * 
   * @return
   */
  private TypedTableModel getMasterTableModel() {
    // Initialization of Model
    TypedTableModel model =
      new TypedTableModel(new String[] {"shortTitle", "currentDate", "projectSubmission", "imageUrl"},
        new Class[] {String.class, String.class, String.class, String.class});
    // Set short title
    String shortTitle = "";
    if (project.getShortName() != null && !project.getShortName().isEmpty()) {
      shortTitle += project.getShortName() + " - ";
    }
    if (loggedCenter.getAcronym() != null && !loggedCenter.getAcronym().isEmpty()) {
      shortTitle += loggedCenter.getAcronym() + " - ";
    }
    shortTitle += "P" + Long.toString(projectID);

    // Set currentDate
    String currentDate = "";
    // Get datetime
    ZonedDateTime timezone = ZonedDateTime.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-d 'at' HH:mm ");
    currentDate = timezone.format(format) + this.getTimeZone();

    // Set projectSubmission
    String projectSubmission = "";

    // set CIAT imgage URL from repo
    String imageUrl = this.getBaseUrl() + "/images/global/centers/CIAT.png";

    model.addRow(new Object[] {shortTitle, currentDate, projectSubmission, imageUrl});
    return model;
  }

  private TypedTableModel getPartnersTableModel() {
    TypedTableModel model =
      new TypedTableModel(new String[] {"partnerName", "partnerType", "institution_id", "project_id"},
        new Class[] {String.class, String.class, Long.class, Long.class});

    for (ProjectPartner projectPartner : project.getProjectPartners().stream().filter(pp -> pp.isActive())
      .collect(Collectors.toList())) {
      String partnerName = projectPartner.getInstitution().getComposedName();
      Long institution_id = projectPartner.getId();
      String partnerType = null;
      if (projectPartner.isInternal()) {
        partnerType = "Internal";
      }
      if (!projectPartner.isInternal()) {
        partnerType = "External";
      }
      model.addRow(new Object[] {partnerName, partnerType, institution_id, project.getId()});
    }
    return model;
  }

  @Override
  public void prepare() {
    loggedCenter = (ResearchCenter) this.getSession().get(APConstants.SESSION_CENTER);
    loggedCenter = centerService.getCrpById(loggedCenter.getId());

    try {
      projectID = Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_ID)));
    } catch (Exception e) {
      projectID = -1;
      LOG.error("Failed to get parameter" + APConstants.PROJECT_ID + ". Exception: " + e.getMessage());
      throw e;
    }
    try {
      project = projectService.getProjectById(projectID);
    } catch (Exception e) {
      LOG.error("Failed to get project from Database. Exception: " + e.getMessage());
      throw e;
    }
    // Calculate time to generate report
    startTime = System.currentTimeMillis();
    LOG.info(
      "Start report download: " + this.getFileName() + ". User: " + this.getCurrentUser().getComposedCompleteName());
  }

  public void setBytesPDF(byte[] bytesPDF) {
    this.bytesPDF = bytesPDF;
  }

}