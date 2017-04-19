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
import org.cgiar.ccafs.marlo.action.json.global.ManageUsersAction;
import org.cgiar.ccafs.marlo.config.APConfig;
import org.cgiar.ccafs.marlo.config.PentahoListener;
import org.cgiar.ccafs.marlo.data.model.ImpactPathwayCyclesEnum;
import org.cgiar.ccafs.marlo.data.model.ResearchCycle;
import org.cgiar.ccafs.marlo.data.model.ResearchImpact;
import org.cgiar.ccafs.marlo.data.model.ResearchImpactObjective;
import org.cgiar.ccafs.marlo.data.model.ResearchLeader;
import org.cgiar.ccafs.marlo.data.model.ResearchOutcome;
import org.cgiar.ccafs.marlo.data.model.ResearchOutput;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.model.ResearchTopic;
import org.cgiar.ccafs.marlo.data.model.Submission;
import org.cgiar.ccafs.marlo.data.service.IProgramService;
import org.cgiar.ccafs.marlo.data.service.IResearchCycleService;
import org.cgiar.ccafs.marlo.utils.APConstants;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
public class ImpactSubmissionSummaryAction extends BaseAction implements Summary {

  private static final long serialVersionUID = -624982650510682813L;
  private static Logger LOG = LoggerFactory.getLogger(ManageUsersAction.class);

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

  @Inject
  public ImpactSubmissionSummaryAction(APConfig config, IProgramService programService,
    IResearchCycleService cycleService) {
    super(config);
    this.programService = programService;
    this.cycleService = cycleService;
  }

  @Override
  public String execute() throws Exception {

    // Calculate time to generate report
    long startTime = System.currentTimeMillis();
    System.out.println("Inicia conteo en: " + (startTime - System.currentTimeMillis()));


    ByteArrayOutputStream os = new ByteArrayOutputStream();
    ResourceManager manager =
      (ResourceManager) ServletActionContext.getServletContext().getAttribute(PentahoListener.KEY_NAME);
    // manager.registerDefaults();
    try {
      String res = this.getClass().getResource("/pentaho/impactPathway.prpt").toString();

      Resource reportResource =
        manager.createDirectly(this.getClass().getResource("/pentaho/impactPathway.prpt"), MasterReport.class);

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
      this.fillSubreport((SubReport) hm.get("program_impact"), "program_impact");
      // Hiden section
      // this.fillSubreport((SubReport) hm.get("research_topics"), "research_topics");
      this.fillSubreport((SubReport) hm.get("outcomes"), "outcomes");
      this.fillSubreport((SubReport) hm.get("outputs"), "outputs");

      PdfReportUtil.createPDF(masterReport, os);
      bytesPDF = os.toByteArray();
      os.close();
    } catch (Exception e) {
      long stopTime = System.currentTimeMillis();
      long elapsedTime = stopTime - startTime;
      System.out.println("Tiempo de ejecución: Error time " + elapsedTime);
      LOG.error("Generating PDF" + e.getMessage());
      throw e;
    }
    // Calculate time of generation
    long stopTime = System.currentTimeMillis();
    long elapsedTime = stopTime - startTime;
    System.out.println("Tiempo de ejecución: " + elapsedTime);
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
    fileName.append("Impact_Pathway_Report-");
    // fileName.append(project.getCrp().getName() + "-");
    // fileName.append("P" + projectID + "-");
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
    TypedTableModel model = new TypedTableModel(new String[] {"title", "current_date", "impact_submission", "imageUrl"},
      new Class[] {String.class, String.class, String.class, String.class});
    String title = "";
    String current_date = "";
    String impact_submission = "";

    // Get title composed by center-area-program
    if (researchProgram.getResearchArea() != null) {
      title += researchProgram.getResearchArea().getName() + " - ";
      if (researchProgram.getComposedName() != null) {
        title += researchProgram.getName();
      }
    }

    // Get datetime
    ZonedDateTime timezone = ZonedDateTime.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-d 'at' HH:mm ");
    current_date = timezone.format(format) + this.getTimeZone();

    // Get submission
    researchCycle = cycleService.getResearchCycleById(ImpactPathwayCyclesEnum.IMPACT_PATHWAY.getId());

    // Filling submission
    List<Submission> submissions = new ArrayList<>();
    for (Submission submission : researchProgram.getSubmissions().stream()
      .filter(s -> s.getResearchCycle().getId() == researchCycle.getId() && s.getYear() == this.getYear())
      .collect(Collectors.toList())) {
      submissions.add(submission);
    }


    if (!submissions.isEmpty()) {
      if (submissions.size() > 1) {
        LOG.error("More than one submission was found, the report will retrieve the first one");
      }
      Submission fisrtSubmission = submissions.get(0);
      String submissionDate = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm").format(fisrtSubmission.getDateTime());

      impact_submission = "Submitted on " + submissionDate + " (" + fisrtSubmission.getResearchCycle().getName()
        + " cycle " + fisrtSubmission.getYear() + ")";
    } else {
      impact_submission =
        "Submission for " + researchCycle.getName() + " cycle " + this.getYear() + ": &lt;pending&gt;";
    }

    // Get CIAT imgage URL from repo
    String imageUrl = null;

    imageUrl = this.getBaseUrl() + "/images/global/centers/CIAT.png";

    model.addRow(new Object[] {title, current_date, impact_submission, imageUrl});
    return model;
  }

  private TypedTableModel getOutcomesTableModel() {
    // Initialization of Model
    TypedTableModel model = new TypedTableModel(
      new String[] {"id", "statement", "research_topic", "program_impact", "target_unit", "target_value",
        "target_year"},
      new Class[] {long.class, String.class, String.class, String.class, String.class, String.class, String.class});


    // Get research topics and then outcomes
    for (ResearchTopic researchTopic : researchProgram.getResearchTopics().stream()
      .filter(rt -> rt.isActive() && rt.getResearchOutcomes().size() > 0).collect(Collectors.toList())) {

      for (ResearchOutcome researchOutcome : researchTopic.getResearchOutcomes().stream().filter(ro -> ro.isActive())
        .collect(Collectors.toList())) {
        long id = 0;
        String statement = "";
        String research_topic = "";
        String program_impact = "";
        String target_unit = "";
        String target_value = "";
        String target_year = "";

        id = researchOutcome.getId();
        statement = researchOutcome.getDescription();
        if (statement.isEmpty()) {
          statement = "&lt;Not Defined&gt;";
        }
        research_topic = researchTopic.getResearchTopic();
        if (research_topic.isEmpty()) {
          research_topic = "&lt;Not Defined&gt;";
        }
        if (researchOutcome.getResearchImpact() != null) {
          program_impact = researchOutcome.getResearchImpact().getDescription();
        }
        if (program_impact.isEmpty()) {
          program_impact = "&lt;Not Defined&gt;";
        }
        if (researchOutcome.getTargetUnit() != null) {
          target_unit = researchOutcome.getTargetUnit().getName();
        }
        if (target_unit.isEmpty()) {
          target_unit = "&lt;Not Defined&gt;";
        }

        if (target_unit.equals("Not Applicable")) {
          target_value = "Not Applicable";
        } else {
          if (researchOutcome.getValue() != null) {
            target_value = researchOutcome.getValue().toString();
          }
        }

        if (target_value == null || target_value.isEmpty()) {
          target_value = "&lt;Not Defined&gt;";
        }
        if (researchOutcome.getTargetYear() != null) {
          target_year = researchOutcome.getTargetYear().toString();
        }
        if (target_year.isEmpty()) {
          target_year = "&lt;Not Defined&gt;";
        }
        model
          .addRow(new Object[] {id, statement, research_topic, program_impact, target_unit, target_value, target_year});
      }

    }


    return model;
  }

  private TypedTableModel getOutputsTableModel() {
    // Initialization of Model
    TypedTableModel model =
      new TypedTableModel(new String[] {"id", "title", "research_topic", "outcome", "contact_persons"},
        new Class[] {long.class, String.class, String.class, String.class, String.class});

    for (ResearchTopic researchTopic : researchProgram.getResearchTopics().stream().filter(rt -> rt.isActive())
      .collect(Collectors.toList())) {
      for (ResearchOutcome researchOutcome : researchTopic.getResearchOutcomes().stream().filter(ro -> ro.isActive())
        .collect(Collectors.toList())) {
        for (ResearchOutput researchOutput : researchOutcome.getResearchOutputs().stream().filter(ro -> ro.isActive())
          .collect(Collectors.toList())) {
          long id = 0;
          String title = "";
          String research_topic = "";
          String outcome = "";
          String contact_persons = "";

          id = researchOutput.getId();
          if (researchOutput.getTitle() != null) {
            title = researchOutput.getTitle();
          }
          if (title.isEmpty()) {
            title = "&lt;Not Defined&gt;";
          }

          if (researchTopic.getResearchTopic() != null) {
            research_topic = researchTopic.getResearchTopic();
          }
          if (research_topic.isEmpty()) {
            research_topic = "&lt;Not Defined&gt;";
          }

          if (researchOutcome.getDescription() != null) {
            outcome = researchOutcome.getDescription();
          }
          if (outcome.isEmpty()) {
            outcome = "&lt;Not Defined&gt;";
          }

          for (ResearchLeader researchLeader : researchProgram.getResearchLeaders().stream().filter(rl -> rl.isActive())
            .collect(Collectors.toList())) {
            if (researchLeader.getUser() != null) {
              contact_persons += researchLeader.getUser().getComposedName() + "<br>";
            }
          }

          if (contact_persons.isEmpty()) {
            contact_persons = "&lt;Not Defined&gt;";
          }

          model.addRow(new Object[] {id, title, research_topic, outcome, contact_persons});
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
      new TypedTableModel(new String[] {"title", "objectives", "program_id", "research_impact_id"},
        new Class[] {String.class, String.class, Long.class, Long.class});
    for (ResearchImpact researchImpact : researchProgram.getResearchImpacts().stream().filter(rp -> rp.isActive())
      .collect(Collectors.toList())) {
      String title = "";
      String objectives = "";
      long program_id = 0;
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
        program_id = researchProgram.getId();
      }
      model.addRow(new Object[] {title, objectives, program_id, researchImpact.getId()});

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
    long programID = -1;
    try {
      programID = Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.CENTER_PROGRAM_ID)));
      researchProgram = programService.getProgramById(programID);
    } catch (Exception e) {
      LOG.error(e.getMessage());
    }
  }

  public void setBytesPDF(byte[] bytesPDF) {
    this.bytesPDF = bytesPDF;
  }

  public void setResearchProgram(ResearchProgram researchProgram) {
    this.researchProgram = researchProgram;
  }

}