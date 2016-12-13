/*****************************************************************
 * isCompleteImpact * This file is part of Managing Agricultural Research for Learning &
 * Outcomes Platform (MARLO).
 * MARLO is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * MARLO is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with MARLO. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/
package org.cgiar.ccafs.marlo.action;

import org.cgiar.ccafs.marlo.config.APConfig;
import org.cgiar.ccafs.marlo.data.IAuditLog;
import org.cgiar.ccafs.marlo.data.model.Auditlog;
import org.cgiar.ccafs.marlo.data.model.ImpactPathwayCyclesEnum;
import org.cgiar.ccafs.marlo.data.model.ImpactPathwaySectionsEnum;
import org.cgiar.ccafs.marlo.data.model.ResearchCenter;
import org.cgiar.ccafs.marlo.data.model.ResearchCycle;
import org.cgiar.ccafs.marlo.data.model.ResearchImpact;
import org.cgiar.ccafs.marlo.data.model.ResearchOutcome;
import org.cgiar.ccafs.marlo.data.model.ResearchOutput;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.model.ResearchTopic;
import org.cgiar.ccafs.marlo.data.model.SectionStatus;
import org.cgiar.ccafs.marlo.data.model.Submission;
import org.cgiar.ccafs.marlo.data.model.User;
import org.cgiar.ccafs.marlo.data.service.IAuditLogService;
import org.cgiar.ccafs.marlo.data.service.IProgramService;
import org.cgiar.ccafs.marlo.data.service.IResearchCycleService;
import org.cgiar.ccafs.marlo.data.service.IResearchImpactService;
import org.cgiar.ccafs.marlo.data.service.IResearchOutcomeService;
import org.cgiar.ccafs.marlo.data.service.IResearchOutputService;
import org.cgiar.ccafs.marlo.data.service.IResearchTopicService;
import org.cgiar.ccafs.marlo.data.service.ISectionStatusService;
import org.cgiar.ccafs.marlo.security.APCustomRealm;
import org.cgiar.ccafs.marlo.security.BaseSecurityContext;
import org.cgiar.ccafs.marlo.security.Permission;
import org.cgiar.ccafs.marlo.security.SessionCounter;
import org.cgiar.ccafs.marlo.security.UserToken;
import org.cgiar.ccafs.marlo.utils.APConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This action aims to define general functionalities that are going to be used
 * by all other Actions.
 * 
 * @author Hernán David Carvajal
 * @author Héctor Fabio Tobón R.
 * @author Christian Garcia
 */
public class BaseAction extends ActionSupport implements Preparable, SessionAware, ServletRequestAware {

  private static final long serialVersionUID = -740360140511380630L;

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(BaseAction.class);

  public static final String CANCEL = "cancel";
  public static final String NEXT = "next";
  public static final String NOT_AUTHORIZED = "403";
  public static final String NOT_FOUND = "404";
  public static final String NOT_LOGGED = "401";
  public static final String SAVED_STATUS = "savedStatus";

  @Inject
  protected BaseSecurityContext securityContext;
  @Inject
  private IAuditLogService auditLogManager;
  @Inject
  private IResearchTopicService topicService;
  @Inject
  private IResearchImpactService impactService;
  @Inject
  private IResearchOutcomeService outcomeService;
  @Inject
  private IResearchOutputService outputService;
  @Inject
  private ISectionStatusService secctionStatusService;
  @Inject
  private IResearchCycleService cycleService;
  @Inject
  private IProgramService programService;
  @Inject
  private ISectionStatusService sectionStatusService;

  protected boolean add;
  private String basePermission;

  protected boolean cancel;
  private boolean canEdit;
  protected APConfig config;
  private Long centerID;
  private String centerSession;
  private ResearchCenter currentCenter;
  protected boolean dataSaved;
  protected boolean delete;
  private boolean draft;
  private boolean fullEditable;
  private boolean isEditable;
  // Justification of the changes
  private String justification;
  protected boolean next;
  private Map<String, Object> parameters;
  private HttpServletRequest request;

  protected boolean save;
  private boolean saveable; // If user is able to see the save, cancel, delete

  // Set the invalid fields
  private HashMap<String, String> invalidFields;

  private Map<String, Object> session;
  protected boolean submit;
  private Submission submission;

  @Inject
  public BaseAction(APConfig config) {
    this.config = config;
    this.saveable = true;
    this.fullEditable = true;
    this.justification = "";
  }

  /* Override this method depending of the save action. */
  public String add() {
    return SUCCESS;
  }

  /**
   * This function add a flag (--warn--) to the message in order to give a
   * different style to the success message using javascript once the html is
   * ready.
   * 
   * @param message
   */
  public void addActionWarning(String message) {
    this.addActionMessage("--warn--" + message);
  }

  public boolean canAccessSuperAdmin() {
    return this.securityContext.hasAllPermissions(Permission.FULL_PRIVILEGES);
  }

  public boolean canAcessImpactPathway() {

    String permission = this.generatePermission(Permission.RESEARCH_AREA_FULL_PRIVILEGES, this.getCenterSession());
    LOG.debug(permission);
    LOG.debug(String.valueOf(securityContext.hasPermission(permission)));
    return securityContext.hasPermission(permission);
  }

  /**
   * Verify if the class model name have not relations for enable the delete button.
   * 
   * @param id - the id of the model
   * @param className - the model class name
   * @return true for enabling the delete button or false to disable the delete button.
   */
  public boolean canBeDeleted(long id, String className) {
    Class clazz;
    try {

      clazz = Class.forName(className);

      // Verify ResearchTopic Model
      if (clazz == ResearchTopic.class) {
        ResearchTopic topic = topicService.getResearchTopicById(id);

        List<ResearchOutcome> outcomes = new ArrayList<>(
          topic.getResearchOutcomes().stream().filter(ro -> ro.isActive()).collect(Collectors.toList()));

        if (outcomes != null) {
          if (!outcomes.isEmpty()) {
            return false;
          }
        }
      }

      // Verify ResearchImpact Model
      if (clazz == ResearchImpact.class) {
        ResearchImpact impact = impactService.getResearchImpactById(id);

        List<ResearchOutcome> outcomes = new ArrayList<>(
          impact.getResearchOutcomes().stream().filter(ro -> ro.isActive()).collect(Collectors.toList()));

        if (outcomes != null) {
          if (!outcomes.isEmpty()) {
            return false;
          }
        }
      }

      // Verify ResearchOutcome Model
      if (clazz == ResearchOutcome.class) {
        ResearchOutcome outcome = outcomeService.getResearchOutcomeById(id);

        List<ResearchOutput> outputs = new ArrayList<>(
          outcome.getResearchOutputs().stream().filter(ro -> ro.isActive()).collect(Collectors.toList()));

        if (outputs != null) {
          if (!outputs.isEmpty()) {
            return false;
          }
        }
      }

      return true;
    } catch (Exception e) {
      return false;
    }


  }

  /* Override this method depending of the cancel action. */
  public String cancel() {
    return CANCEL;
  }

  /**
   * This method clears the cache and re-load the user permissions in the next
   * iteration.
   */
  public void clearPermissionsCache() {
    ((APCustomRealm) securityContext.getRealm())
      .clearCachedAuthorizationInfo(securityContext.getSubject().getPrincipals());
  }

  /* Override this method depending of the delete action. */
  public String delete() {
    return SUCCESS;
  }

  @Override
  public String execute() throws Exception {
    if (save) {
      return this.save();
    } else if (delete) {
      return this.delete();
    } else if (cancel) {
      return this.cancel();
    } else if (next) {
      return this.next();
    } else if (add) {
      return this.add();
    } else if (submit) {
      return this.submit();
    }
    return INPUT;
  }


  public String generatePermission(String permission, String... params) {
    // TODO: Update the permission
    if (params != null) {
      return this.getText(permission, params);
    } else {
      return this.getText(permission);
    }

  }


  public String getActionName() {
    return ServletActionContext.getActionMapping().getName();
  }

  /**
   * This method calculates all the years between the start date and the end date.
   * 
   * @return a List of numbers representing all the years, or an empty list if nothing found.
   */
  public List<Integer> getAllYears() {
    List<Integer> allYears = new ArrayList<>();

    Calendar calendarStart = Calendar.getInstance();
    calendarStart.set(Calendar.YEAR, 2017);
    Calendar calendarEnd = Calendar.getInstance();
    calendarEnd.set(Calendar.YEAR, 2050);

    while (calendarStart.get(Calendar.YEAR) <= calendarEnd.get(Calendar.YEAR)) {
      // Adding the year to the list.
      allYears.add(calendarStart.get(Calendar.YEAR));
      // Adding a year (365 days) to the start date.
      calendarStart.add(Calendar.YEAR, 1);
    }

    return allYears;
  }

  public String getBasePermission() {
    return basePermission;
  }

  public String getBaseUrl() {
    return config.getBaseUrl();
  }

  /**
   * Get the center that is currently save in the session, if the user access to
   * the platform whit a diferent url, get the current action to catch the center
   * 
   * @return the center that the user has log in
   */
  public String getCenterSession() {
    if (session != null && !session.isEmpty()) {
      try {
        ResearchCenter center = (ResearchCenter) session.get(APConstants.SESSION_CENTER) != null
          ? (ResearchCenter) session.get(APConstants.SESSION_CENTER) : null;
        // Assumed there is only one center in the system, the default one.
        this.centerSession = center.getAcronym();
      } catch (Exception e) {
        LOG.warn("There was a problem trying to find the user's center in the session.");
      }
    } else {
      String actionName = this.getActionName();
      if (actionName.split("/").length > 1) {
        this.centerSession = actionName.split("/")[0];
      }
    }
    return this.centerSession;
  }

  public APConfig getConfig() {
    return config;
  }

  /**
   * Get the center that is currently save in the session, if the user access to
   * the platform whit a diferent url, get the current action to catch the center
   * 
   * @return the center that the user has log in
   */
  public Long getCrpID() {
    if (session != null && !session.isEmpty()) {
      try {
        ResearchCenter center = (ResearchCenter) session.get(APConstants.SESSION_CENTER) != null
          ? (ResearchCenter) session.get(APConstants.SESSION_CENTER) : null;
        this.centerID = center.getId();
      } catch (Exception e) {
        LOG.warn("There was a problem trying to find the user center in the session.");
      }
    } else {

      this.centerID = null;

    }
    return this.centerID;
  }

  public ResearchCenter getCurrentCenter() {
    if (session != null && !session.isEmpty()) {
      try {
        ResearchCenter center = (ResearchCenter) session.get(APConstants.SESSION_CENTER) != null
          ? (ResearchCenter) session.get(APConstants.SESSION_CENTER) : null;
        this.currentCenter = center;
      } catch (Exception e) {
        LOG.warn("There was a problem trying to find the user center in the session.");
      }
    } else {

      this.currentCenter = null;

    }
    return this.currentCenter;
  }

  /**
   * Get the user that is currently saved in the session.
   * 
   * @return a user object or null if no user was found.
   */
  public User getCurrentUser() {
    User u = null;
    if (session != null && !session.isEmpty()) {
      try {
        u = session.get(APConstants.SESSION_USER) != null ? (User) session.get(APConstants.SESSION_USER) : null;
      } catch (Exception e) {
        LOG.warn("There was a problem trying to find the user in the session.");
      }
    }
    return u;
  }

  public HashMap<String, String> getInvalidFields() {
    return invalidFields;
  }

  public List<Auditlog> getListLog(IAuditLog object) {
    try {
      return auditLogManager.listLogs(object.getClass(), Long.parseLong(object.getId().toString()),
        this.getActionName());
    } catch (Exception e) {
      return new ArrayList<Auditlog>();
    }
  }

  /**
   * Define default locale while we decide to support other languages in the
   * future.
   */
  @Override
  public Locale getLocale() {
    return Locale.ENGLISH;
  }

  public String getNamespace() {
    return ServletActionContext.getActionMapping().getNamespace();
  }

  /**
   * get the number of users log in in the application
   * 
   * @return the number of users online
   */
  public int getOnline() {
    if (SessionCounter.users != null) {
      return SessionCounter.users.size();
    }
    return 0;
  }


  /**
   * This method gets the specific section status from the sectionStatuses array for a Deliverable.
   * 
   * @param deliverableID is the deliverable ID to be identified.
   * @param section is the name of some section.
   * @return a SectionStatus object with the information requested.
   */
  public SectionStatus getOutcomeStatus(long outcomeID) {

    ResearchOutcome outcome = outcomeService.getResearchOutcomeById(outcomeID);
    List<SectionStatus> sectionStatuses;
    if (outcome.getSectionStatuses() != null) {
      sectionStatuses = new ArrayList<>(
        outcome.getSectionStatuses().stream().filter(c -> c.getYear() == this.getYear()).collect(Collectors.toList()));
    } else {
      return null;
    }

    if (!sectionStatuses.isEmpty()) {
      return sectionStatuses.get(0);
    }
    return null;
  }

  /**
   * This method gets the specific section status from the sectionStatuses array for a Deliverable.
   * 
   * @param deliverableID is the deliverable ID to be identified.
   * @param section is the name of some section.
   * @return a SectionStatus object with the information requested.
   */
  public SectionStatus getOutputStatus(long outputID) {

    ResearchOutput output = outputService.getResearchOutputById(outputID);
    List<SectionStatus> sectionStatuses;

    if (output.getSectionStatuses() != null) {
      sectionStatuses = new ArrayList<>(
        output.getSectionStatuses().stream().filter(c -> c.getYear() == this.getYear()).collect(Collectors.toList()));
    } else {
      return null;
    }

    if (!sectionStatuses.isEmpty()) {
      return sectionStatuses.get(0);
    }
    return null;
  }

  public Map<String, Object> getParameters() {
    parameters = ActionContext.getContext().getParameters();
    return parameters;
  }

  public String getParameterValue(String param) {
    Object paramObj = this.getParameters().get(param);
    if (paramObj == null) {
      return null;
    }
    return ((String[]) paramObj)[0];
  }

  public HttpServletRequest getRequest() {
    return request;
  }

  public boolean getSectionStatusIP(String section, long programID) {
    ResearchProgram program = programService.getProgramById(programID);

    if (ImpactPathwaySectionsEnum.getValue(section) == null) {
      return false;
    }

    switch (ImpactPathwaySectionsEnum.getValue(section)) {
      case PROGRAM_IMPACT:
        return this.validateImpact(program, section);
      case TOPIC:
        return this.validateTopic(program, section);
      case OUTCOME:
        return this.validateOutcome(program);
      case OUTPUT:
        return this.validateOutput(program);
    }

    return true;
  }

  public BaseSecurityContext getSecurityContext() {
    return securityContext;
  }

  public Map<String, Object> getSession() {
    return session;
  }

  public Submission getSubmission() {
    return submission;
  }

  public List<UserToken> getUsersOnline() {
    return SessionCounter.users;
  }

  /**
   * Return the artifact version of the Marlo project pom.xml
   * 
   * @return the actual Marlo version
   */
  public String getVersion() {
    String version = this.getClass().getPackage().getImplementationVersion();
    if (version == null) {
      Properties prop = new Properties();
      try {
        prop.load(ServletActionContext.getServletContext().getResourceAsStream("/META-INF/MANIFEST.MF"));
        version = prop.getProperty("Implementation-Version");
      } catch (IOException e) {
        LOG.warn("MAINFEST file Does not exist");
      }
    }
    return version;
  }

  public int getYear() {
    return Calendar.getInstance().get(Calendar.YEAR);
  }

  public boolean hasPermission(String fieldName) {
    if (basePermission == null) {
      return securityContext.hasPermission(fieldName);
    } else {
      return securityContext.hasPermission(this.getBasePermission() + ":" + fieldName);
    }
  }

  public boolean hasPersmissionSubmit() {
    boolean permissions = this.hasPermission("submit");
    return permissions;
  }

  public boolean hasPersmissionSubmitIP(long programID) {
    ResearchProgram program = programService.getProgramById(programID);
    String permission =
      this.generatePermission(Permission.RESEARCH_PROGRAM_SUBMISSION_PERMISSION, this.getCurrentCenter().getAcronym(),
        String.valueOf(program.getResearchArea().getId()), String.valueOf(programID));
    boolean permissions = this.securityContext.hasPermission(permission);
    return permissions;
  }

  /**
   * @param role
   * @return true if is the user role
   */
  public boolean isAdmin() {
    return securityContext.hasRole("Admin");
  }

  public boolean isCanEdit() {
    return canEdit;
  }

  public boolean isCompleteIP(long programID) {

    if (sectionStatusService.findAll() == null) {
      return false;
    }

    ResearchProgram researchProgram = programService.getProgramById(programID);

    List<String> statuses = secctionStatusService.distinctSectionStatus(programID);

    if (statuses.size() != 4) {
      return false;
    }


    if (!this.validateOutcome(researchProgram)) {
      return false;
    }

    if (!this.validateOutput(researchProgram)) {
      return false;
    }

    List<SectionStatus> sectionStatuses = new ArrayList<>(researchProgram.getSectionStatuses().stream()
      .filter(ss -> ss.getYear() == (short) this.getYear()).collect(Collectors.toList()));

    if (sectionStatuses != null && sectionStatuses.size() > 0) {
      for (SectionStatus sectionStatus : sectionStatuses) {
        if (sectionStatus.getMissingFields().length() > 0) {
          return false;
        }
      }
    } else {
      return false;
    }
    return true;
  }

  public boolean isEditable() {
    return isEditable;
  }

  protected boolean isHttpPost() {
    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
      return true;
    }
    return false;
  }

  /**
   * Validate if the user is already logged in or not.
   * 
   * @return true if the user is logged in, false otherwise.
   */
  public boolean isLogged() {
    if (this.getCurrentUser() == null) {
      return false;
    }
    return true;
  }

  public boolean isSubmit() {
    return submit;
  }

  public boolean isSubmitIP(long programID) {

    ResearchProgram program = programService.getProgramById(programID);
    if (program != null) {

      ResearchCycle cycle = cycleService.getResearchCycleById(ImpactPathwayCyclesEnum.IMPACT_PATHWAY.getId());

      List<Submission> submissions = new ArrayList<>(program.getSubmissions().stream()
        .filter(s -> s.getResearchCycle().equals(cycle) && s.getYear().intValue() == this.getYear())
        .collect(Collectors.toList()));

      if (submissions != null && submissions.size() > 0) {
        this.setSubmission(submissions.get(0));
        return true;
      }
    }
    return false;
  }


  public String next() {
    return NEXT;
  }

  @Override
  public void prepare() throws Exception {
    // So far, do nothing here!
  }

  /* Override this method depending of the save action. */
  public String save() {
    return SUCCESS;
  }


  public void setAdd(boolean add) {
    this.add = true;
  }

  public void setBasePermission(String basePermission) {
    this.basePermission = basePermission;
  }

  public void setCancel(boolean cancel) {
    this.cancel = true;
  }

  public void setCanEdit(boolean canEdit) {
    this.canEdit = canEdit;
  }

  public void setCenterSession(String centerSession) {
    this.centerSession = centerSession;
  }

  public void setDataSaved(boolean dataSaved) {
    this.dataSaved = dataSaved;
  }

  public void setDelete(boolean delete) {
    this.delete = delete;
  }

  public void setDraft(boolean draft) {
    this.draft = draft;
  }

  public void setEditable(boolean isEditable) {
    this.isEditable = isEditable;
  }

  public void setEditableParameter(boolean isEditable) {
    this.isEditable = isEditable;
  }

  public void setFullEditable(boolean fullEditable) {
    this.fullEditable = fullEditable;
  }

  public void setInvalidFields(HashMap<String, String> invalidFields) {
    this.invalidFields = invalidFields;
  }

  public void setJustification(String justification) {
    this.justification = justification;
  }

  public void setNext(boolean next) {
    this.next = true;
  }

  public void setSave(boolean save) {
    this.save = true;
  }

  public void setSaveable(boolean saveable) {
    this.saveable = saveable;
  }

  public void setSecurityContext(BaseSecurityContext securityContext) {
    this.securityContext = securityContext;
  }

  @Override
  public void setServletRequest(HttpServletRequest request) {
    this.request = request;
  }

  @Override
  public void setSession(Map<String, Object> session) {
    this.session = session;
  }

  public void setSubmission(Submission submission) {
    this.submission = submission;
  }

  public void setSubmit(boolean submit) {
    this.submit = true;
  }

  public String submit() {
    return SUCCESS;
  }

  public boolean validateImpact(ResearchProgram program, String sectionName) {

    SectionStatus sectionStatus =
      secctionStatusService.getSectionStatusByProgram(program.getId(), sectionName, this.getYear());

    if (sectionStatus == null) {
      return false;
    }
    if (sectionStatus.getMissingFields().length() != 0) {
      return false;
    }

    return true;
  }

  public boolean validateOutcome(ResearchProgram program) {
    if (program != null) {
      List<ResearchTopic> topics =
        new ArrayList<>(program.getResearchTopics().stream().filter(rt -> rt.isActive()).collect(Collectors.toList()));
      if (topics != null && !topics.isEmpty()) {
        for (ResearchTopic researchTopic : topics) {
          List<ResearchOutcome> outcomes = new ArrayList<>(
            researchTopic.getResearchOutcomes().stream().filter(ro -> ro.isActive()).collect(Collectors.toList()));
          if (outcomes != null && !outcomes.isEmpty()) {
            for (ResearchOutcome researchOutcome : outcomes) {
              SectionStatus sectionStatus = this.getOutcomeStatus(researchOutcome.getId());
              if (sectionStatus == null) {
                return false;
              } else {
                if (sectionStatus.getMissingFields().length() != 0) {
                  return false;
                }
              }
            }
          } else {
            return false;
          }
        }
      } else {
        return false;
      }
    } else {
      return false;
    }

    return true;
  }

  public boolean validateOutput(ResearchProgram program) {

    if (program != null) {
      List<ResearchTopic> topics =
        new ArrayList<>(program.getResearchTopics().stream().filter(rt -> rt.isActive()).collect(Collectors.toList()));
      if (topics != null && !topics.isEmpty()) {
        for (ResearchTopic researchTopic : topics) {
          List<ResearchOutcome> outcomes = new ArrayList<>(
            researchTopic.getResearchOutcomes().stream().filter(ro -> ro.isActive()).collect(Collectors.toList()));
          if (outcomes != null && !outcomes.isEmpty()) {
            for (ResearchOutcome researchOutcome : outcomes) {
              researchOutcome.setMilestones(new ArrayList<>(researchOutcome.getResearchMilestones().stream()
                .filter(rm -> rm.isActive()).collect(Collectors.toList())));

              List<ResearchOutput> outputs = new ArrayList<>(
                researchOutcome.getResearchOutputs().stream().filter(ro -> ro.isActive()).collect(Collectors.toList()));
              if (outputs != null && !outputs.isEmpty()) {
                for (ResearchOutput researchOutput : outputs) {
                  SectionStatus sectionStatus = this.getOutputStatus(researchOutput.getId());
                  if (sectionStatus == null) {
                    return false;
                  } else {
                    if (sectionStatus.getMissingFields().length() != 0) {
                      return false;
                    }
                  }
                }
              } else {
                return false;
              }
            }
          } else {
            return false;
          }
        }
      } else {
        return false;
      }
    } else {
      return false;
    }

    return true;

  }

  public boolean validateTopic(ResearchProgram program, String sectionName) {

    SectionStatus sectionStatus =
      secctionStatusService.getSectionStatusByProgram(program.getId(), sectionName, this.getYear());

    if (sectionStatus == null) {
      return false;
    }
    if (sectionStatus.getMissingFields().length() != 0) {
      return false;
    }

    return true;
  }


}
