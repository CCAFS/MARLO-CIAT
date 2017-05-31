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
import org.cgiar.ccafs.marlo.data.model.ResearchCycle;
import org.cgiar.ccafs.marlo.data.model.ResearchImpact;
import org.cgiar.ccafs.marlo.data.model.ResearchImpactObjective;
import org.cgiar.ccafs.marlo.data.model.ResearchOutcome;
import org.cgiar.ccafs.marlo.data.model.ResearchOutput;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.model.ResearchTopic;
import org.cgiar.ccafs.marlo.data.service.IProgramService;
import org.cgiar.ccafs.marlo.data.service.IResearchCycleService;

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
public class ImpactPathwayOutcomesSummaryAction extends BaseAction implements Summary {

  private static final long serialVersionUID = -624982650510682813L;
  private static Logger LOG = LoggerFactory.getLogger(ImpactPathwayOutcomesSummaryAction.class);

  // Streams
  InputStream inputStream;
  // PDF bytes
  private byte[] bytesPDF;
  // Services
  private IProgramService programService;
  private IResearchCycleService cycleService;
  // Params
  private ResearchProgram researchProgram;
  private ResearchCycle researchCycle;
  private long startTime;

  @Inject
  public ImpactPathwayOutcomesSummaryAction(APConfig config, IProgramService programService,
    IResearchCycleService cycleService) {
    super(config);
    this.programService = programService;
    this.cycleService = cycleService;
  }

  @Override
  public String execute() throws Exception {


    ByteArrayOutputStream os = new ByteArrayOutputStream();
    ResourceManager manager =
      (ResourceManager) ServletActionContext.getServletContext().getAttribute(PentahoListener.KEY_NAME);
    // manager.registerDefaults();
    try {

      Resource reportResource =
        manager.createDirectly(this.getClass().getResource("/pentaho/impactPathwayOutcomes.prpt"), MasterReport.class);

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
      case "program_impact":
        model = this.getProgramImpactTableModel();
        break;
      // case "research_topics":
      // model = this.getResearchTopicsTableModel();
      // break;
      case "outcomes":
        model = this.getOutcomesTableModel();
        break;
      case "outputs":
        model = this.getOutputsTableModel();
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

  private File getFile(String fileName) {

    // Get file from resources folder
    ClassLoader classLoader = this.getClass().getClassLoader();
    File file = new File(classLoader.getResource(fileName).getFile());
    return file;
  }

  @Override
  public String getFileName() {
    StringBuffer fileName = new StringBuffer();
    fileName.append("Impact_Pathway_Outcomes_Report-");
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
    TypedTableModel model = new TypedTableModel(new String[] {"title", "current_date", "imageUrl"},
      new Class[] {String.class, String.class, String.class});
    String title = "";
    String currentDate = "";

    // Get title
    title = "";

    // Get datetime
    ZonedDateTime timezone = ZonedDateTime.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-d 'at' HH:mm ");
    currentDate = timezone.format(format) + this.getTimeZone();

    // Get CIAT imgage URL from repo
    String imageUrl = this.getBaseUrl() + "/images/global/centers/CIAT.png";

    model.addRow(new Object[] {title, currentDate, imageUrl});
    return model;
  }

  private TypedTableModel getOutcomesTableModel() {
    // Initialization of Model
    TypedTableModel model = new TypedTableModel(
      new String[] {"shortName", "statement", "research_topic", "program_impact", "target_unit", "target_value",
        "target_year", "showResearchTopic", "id"},
      new Class[] {String.class, String.class, String.class, String.class, String.class, String.class, String.class,
        Boolean.class, Long.class});


    // Get research topics and then outcomes
    for (ResearchTopic researchTopic : researchProgram.getResearchTopics().stream()
      .filter(rt -> rt.isActive() && rt.getResearchOutcomes().size() > 0).collect(Collectors.toList())) {
      String researchTopicTitle = "";
      Boolean showResearchTopic;
      int countOutcome = 0;
      for (ResearchOutcome researchOutcome : researchTopic.getResearchOutcomes().stream().filter(ro -> ro.isActive())
        .collect(Collectors.toList())) {
        if (countOutcome == 0) {
          if (researchTopic.getResearchTopic() != null) {
            researchTopicTitle = researchTopic.getResearchTopic();
          }
          if (researchTopicTitle.isEmpty()) {
            researchTopicTitle = "&lt;Not Defined&gt;";
          }
          showResearchTopic = true;
        } else {
          showResearchTopic = false;
        }


        String shortName = "";
        String statement = "";

        String programImpact = "";
        String targetUnit = "";
        String targetValue = "";
        String targetYear = "";
        Long id = researchOutcome.getId();

        if (researchOutcome.getShortName() != null) {
          shortName = researchOutcome.getShortName();
        }
        if (shortName.isEmpty()) {
          shortName = "&lt;Not Defined&gt;";
        }

        if (researchOutcome.getDescription() != null) {
          statement = researchOutcome.getDescription();
        }
        if (statement.isEmpty()) {
          statement = "&lt;Not Defined&gt;";
        }

        if (researchOutcome.getResearchImpact() != null) {
          programImpact = researchOutcome.getResearchImpact().getDescription();
        }
        if (programImpact.isEmpty()) {
          programImpact = "&lt;Not Defined&gt;";
        }
        if (researchOutcome.getTargetUnit() != null) {
          targetUnit = researchOutcome.getTargetUnit().getName();
        }
        if (targetUnit.isEmpty()) {
          targetUnit = "&lt;Not Defined&gt;";
        }

        if (targetUnit.equals("Not Applicable")) {
          targetValue = "Not Applicable";
        } else {
          if (researchOutcome.getValue() != null) {
            targetValue = researchOutcome.getValue().toString();
          }
        }

        if (targetValue == null || targetValue.isEmpty()) {
          targetValue = "&lt;Not Defined&gt;";
        }
        if (researchOutcome.getTargetYear() != null) {
          targetYear = researchOutcome.getTargetYear().toString();
        }
        if (targetYear.isEmpty()) {
          targetYear = "&lt;Not Defined&gt;";
        }

        countOutcome++;
        model.addRow(new Object[] {shortName, statement, researchTopicTitle, programImpact, targetUnit, targetValue,
          targetYear, showResearchTopic, id});
      }

    }


    return model;
  }

  private TypedTableModel getOutputsTableModel() {
    // Initialization of Model
    TypedTableModel model =
      new TypedTableModel(new String[] {"shortName", "title", "research_topic", "outcome", "showResearchTopic", "id"},
        new Class[] {String.class, String.class, String.class, String.class, Boolean.class, Long.class});

    for (ResearchTopic researchTopic : researchProgram.getResearchTopics().stream().filter(rt -> rt.isActive())
      .collect(Collectors.toList())) {
      String researchTopicTitle = "";
      Boolean showResearchTopic;
      int countOutcome = 0;
      for (ResearchOutcome researchOutcome : researchTopic.getResearchOutcomes().stream().filter(ro -> ro.isActive())
        .collect(Collectors.toList())) {
        for (ResearchOutput researchOutput : researchOutcome.getResearchOutputs().stream().filter(ro -> ro.isActive())
          .collect(Collectors.toList())) {
          Long id = researchOutput.getId();
          if (countOutcome == 0) {
            if (researchTopic.getResearchTopic() != null) {
              researchTopicTitle = researchTopic.getResearchTopic();
            }
            if (researchTopicTitle.isEmpty()) {
              researchTopicTitle = "&lt;Not Defined&gt;";
            }
            showResearchTopic = true;
          } else {
            showResearchTopic = false;
          }

          String shortName = "";
          String title = "";
          String outcome = "";

          if (researchOutput.getShortName() != null) {
            shortName = researchOutput.getShortName();
          }
          if (shortName.isEmpty()) {
            shortName = "&lt;Not Defined&gt;";
          }
          if (researchOutput.getTitle() != null) {
            title = researchOutput.getTitle();
          }
          if (title.isEmpty()) {
            title = "&lt;Not Defined&gt;";
          }


          if (researchOutcome.getDescription() != null) {
            outcome = researchOutcome.getDescription();
          }
          if (outcome.isEmpty()) {
            outcome = "&lt;Not Defined&gt;";
          }
          countOutcome++;

          model.addRow(new Object[] {shortName, title, researchTopicTitle, outcome, showResearchTopic, id});
        }
      }
    }


    return model;
  }

  /**
   * Get the program impact information
   * 
   * @return model with program impact information
   */
  private TypedTableModel getProgramImpactTableModel() {
    // Initialization of Model
    TypedTableModel model =
      new TypedTableModel(new String[] {"title", "objectives", "program_id", "research_impact_id", "shortName"},
        new Class[] {String.class, String.class, Long.class, Long.class, String.class});
    for (ResearchImpact researchImpact : researchProgram.getResearchImpacts().stream().filter(rp -> rp.isActive())
      .collect(Collectors.toList())) {
      String title = "";
      String objectives = "";
      long programId = 0;
      if (researchImpact.getDescription() == null || researchImpact.getDescription().isEmpty()) {
        title = "&lt;Not Defined&gt;";
      } else {
        title = researchImpact.getDescription();
      }

      if (researchImpact.getResearchImpactObjectives() != null
        && researchImpact.getResearchImpactObjectives().size() > 0) {
        for (ResearchImpactObjective researchImpactObjective : researchImpact.getResearchImpactObjectives().stream()
          .filter(rio -> rio.isActive() && rio.getResearchObjective() != null).collect(Collectors.toList())) {
          objectives += "&#9679" + researchImpactObjective.getResearchObjective().getObjective() + "<br>";
        }
      } else {
        objectives = "&lt;Not Defined&gt;";
      }
      if (objectives.isEmpty()) {
        objectives = null;
      }
      if (researchProgram != null) {
        programId = researchProgram.getId();
      }

      String shortName = "";
      if (researchImpact.getShortName() == null || researchImpact.getShortName().isEmpty()) {
        shortName = "&lt;Not Defined&gt;";
      } else {
        shortName = researchImpact.getShortName();
      }


      model.addRow(new Object[] {title, objectives, programId, researchImpact.getId(), shortName});
    }
    return model;
  }

  public ResearchProgram getResearchProgram() {
    return researchProgram;
  }

  private TypedTableModel getResearchTopicsTableModel() {
    // Initialization of Model
    TypedTableModel model = new TypedTableModel(new String[] {"name", "id"}, new Class[] {String.class, String.class});
    String name = "";
    String id = "";

    for (ResearchTopic researchTopic : researchProgram.getResearchTopics().stream()
      .filter(rt -> rt.isActive() && rt.getResearchTopic() != null).collect(Collectors.toList())) {
      name = researchTopic.getResearchTopic();
      id = researchTopic.getId().toString();
      model.addRow(new Object[] {name, id});
    }
    return model;
  }

  @Override
  public void prepare() {
    // Calculate time to generate report
    startTime = System.currentTimeMillis();
    LOG.info(
      "Start report download: " + this.getFileName() + ". User: " + this.getCurrentUser().getComposedCompleteName());
  }

  public void setBytesPDF(byte[] bytesPDF) {
    this.bytesPDF = bytesPDF;
  }

  public void setResearchProgram(ResearchProgram researchProgram) {
    this.researchProgram = researchProgram;
  }

}