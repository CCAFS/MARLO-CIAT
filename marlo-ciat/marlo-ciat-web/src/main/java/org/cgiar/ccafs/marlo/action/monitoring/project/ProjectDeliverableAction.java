/*****************************************************************
 * This file is part of Managing Agricultural Research for Learning &
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

package org.cgiar.ccafs.marlo.action.monitoring.project;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConfig;
import org.cgiar.ccafs.marlo.data.model.Deliverable;
import org.cgiar.ccafs.marlo.data.model.DeliverableDocument;
import org.cgiar.ccafs.marlo.data.model.DeliverableType;
import org.cgiar.ccafs.marlo.data.model.Project;
import org.cgiar.ccafs.marlo.data.model.ResearchArea;
import org.cgiar.ccafs.marlo.data.model.ResearchCenter;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.data.service.IDeliverableDocumentService;
import org.cgiar.ccafs.marlo.data.service.IDeliverableService;
import org.cgiar.ccafs.marlo.data.service.IDeliverableTypeService;
import org.cgiar.ccafs.marlo.data.service.IProjectService;
import org.cgiar.ccafs.marlo.security.Permission;
import org.cgiar.ccafs.marlo.utils.APConstants;
import org.cgiar.ccafs.marlo.utils.AutoSaveReader;
import org.cgiar.ccafs.marlo.validation.monitoring.project.DeliverableValidator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class ProjectDeliverableAction extends BaseAction {


  private static final long serialVersionUID = 6553033204498654741L;


  private IDeliverableService deliverableService;
  private IDeliverableTypeService deliverableTypeService;
  private IDeliverableDocumentService deliverableDocumentService;
  private ICenterService centerService;
  private IProjectService projectService;
  private DeliverableValidator validator;

  private long deliverableID;
  private long projectID;
  private long programID;
  private long areaID;
  private Project project;
  private ResearchArea selectedResearchArea;
  private ResearchProgram selectedProgram;
  private ResearchCenter loggedCenter;
  private Deliverable deliverable;
  private List<ResearchArea> researchAreas;
  private List<ResearchProgram> researchPrograms;
  private List<DeliverableType> deliverableTypes;

  @Inject
  public ProjectDeliverableAction(APConfig config, ICenterService centerService,
    IDeliverableTypeService deliverableTypeService, IDeliverableService deliverableService,
    IProjectService projectService, IDeliverableDocumentService deliverableDocumentService,
    DeliverableValidator validator) {
    super(config);
    this.centerService = centerService;
    this.deliverableTypeService = deliverableTypeService;
    this.deliverableService = deliverableService;
    this.projectService = projectService;
    this.deliverableDocumentService = deliverableDocumentService;
    this.validator = validator;
  }

  @Override
  public String cancel() {

    Path path = this.getAutoSaveFilePath();

    if (path.toFile().exists()) {

      boolean fileDeleted = path.toFile().delete();
    }

    this.setDraft(false);
    Collection<String> messages = this.getActionMessages();
    if (!messages.isEmpty()) {
      String validationMessage = messages.iterator().next();
      this.setActionMessages(null);
      this.addActionMessage("draft:" + this.getText("cancel.autoSave"));
    } else {
      this.addActionMessage("draft:" + this.getText("cancel.autoSave"));
    }
    messages = this.getActionMessages();

    return SUCCESS;
  }

  public long getAreaID() {
    return areaID;
  }

  private Path getAutoSaveFilePath() {
    String composedClassName = project.getClass().getSimpleName();
    String actionFile = this.getActionName().replace("/", "_");
    String autoSaveFile = deliverable.getId() + "_" + composedClassName + "_" + actionFile + ".json";

    return Paths.get(config.getAutoSaveFolder() + autoSaveFile);
  }

  public Deliverable getDeliverable() {
    return deliverable;
  }

  public long getDeliverableID() {
    return deliverableID;
  }

  public List<DeliverableType> getDeliverableTypes() {
    return deliverableTypes;
  }

  public ResearchCenter getLoggedCenter() {
    return loggedCenter;
  }


  public long getProgramID() {
    return programID;
  }

  public Project getProject() {
    return project;
  }


  public long getProjectID() {
    return projectID;
  }


  public List<ResearchArea> getResearchAreas() {
    return researchAreas;
  }


  public List<ResearchProgram> getResearchPrograms() {
    return researchPrograms;
  }

  public ResearchProgram getSelectedProgram() {
    return selectedProgram;
  }

  public ResearchArea getSelectedResearchArea() {
    return selectedResearchArea;
  }

  @Override
  public void prepare() throws Exception {
    loggedCenter = (ResearchCenter) this.getSession().get(APConstants.SESSION_CENTER);
    loggedCenter = centerService.getCrpById(loggedCenter.getId());

    researchAreas = new ArrayList<>(
      loggedCenter.getResearchAreas().stream().filter(ra -> ra.isActive()).collect(Collectors.toList()));

    try {
      deliverableID = Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.DELIVERABLE_ID)));
    } catch (Exception e) {
      deliverableID = -1;
      projectID = -1;
    }

    deliverable = deliverableService.getDeliverableById(deliverableID);

    if (deliverable != null) {
      projectID = deliverable.getProject().getId();
      project = projectService.getProjectById(projectID);

      selectedProgram = project.getResearchProgram();
      programID = selectedProgram.getId();
      selectedResearchArea = selectedProgram.getResearchArea();
      areaID = selectedResearchArea.getId();
      researchPrograms = new ArrayList<>(
        selectedResearchArea.getResearchPrograms().stream().filter(rp -> rp.isActive()).collect(Collectors.toList()));

      deliverableTypes = new ArrayList<>(
        deliverableTypeService.findAll().stream().filter(dt -> dt.isActive()).collect(Collectors.toList()));

      Path path = this.getAutoSaveFilePath();

      if (path.toFile().exists() && this.getCurrentUser().isAutoSave()) {
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(path.toFile()));
        Gson gson = new GsonBuilder().create();
        JsonObject jReader = gson.fromJson(reader, JsonObject.class);
        AutoSaveReader autoSaveReader = new AutoSaveReader();

        deliverable = (Deliverable) autoSaveReader.readFromJson(jReader);

        reader.close();
        this.setDraft(true);

      } else {
        this.setDraft(false);
        deliverable.setDocuments(new ArrayList<>(
          deliverable.getDeliverableDocuments().stream().filter(dd -> dd.isActive()).collect(Collectors.toList())));
      }


    }

    String params[] = {loggedCenter.getAcronym(), selectedResearchArea.getId() + "", selectedProgram.getId() + "",
      projectID + "", deliverableID + ""};
    this.setBasePermission(this.getText(Permission.PROJECT_DEIVERABLE_BASE_PERMISSION, params));

    if (this.isHttpPost()) {
      if (deliverableTypes != null) {
        deliverableTypes.clear();
      }

      if (deliverable.getDocuments() != null) {
        deliverable.getDocuments().clear();
      }
    }

  }

  @Override
  public String save() {
    if (this.hasPermission("*")) {

      Deliverable deliverableDB = deliverableService.getDeliverableById(deliverableID);

      deliverableDB.setName(deliverable.getName());
      deliverableDB.setStartDate(deliverable.getStartDate());
      deliverableDB.setEndDate(deliverable.getEndDate());


      if (deliverable.getDeliverableType().getId() != null) {
        DeliverableType deliverableType =
          deliverableTypeService.getDeliverableTypeById(deliverable.getDeliverableType().getId());
        deliverableDB.setDeliverableType(deliverableType);
      }

      long deliverableSaveID = deliverableService.saveDeliverable(deliverableDB);

      deliverableDB = deliverableService.getDeliverableById(deliverableSaveID);

      this.saveDocuments(deliverableDB);

      Path path = this.getAutoSaveFilePath();

      if (path.toFile().exists()) {
        path.toFile().delete();
      }

      if (!this.getInvalidFields().isEmpty()) {
        this.setActionMessages(null);
        List<String> keys = new ArrayList<String>(this.getInvalidFields().keySet());
        for (String key : keys) {
          this.addActionMessage(key + ": " + this.getInvalidFields().get(key));
        }
      } else {
        this.addActionMessage("message:" + this.getText("saving.saved"));
      }

      return SUCCESS;
    } else {
      return NOT_AUTHORIZED;
    }
  }

  public void saveDocuments(Deliverable deliverableDB) {

    if (deliverableDB.getDeliverableDocuments() != null && deliverableDB.getDeliverableDocuments().size() > 0) {
      List<DeliverableDocument> deliverableDocuments = new ArrayList<>(
        deliverableDB.getDeliverableDocuments().stream().filter(dd -> dd.isActive()).collect(Collectors.toList()));

      for (DeliverableDocument deliverableDocument : deliverableDocuments) {
        if (!deliverable.getDocuments().contains(deliverableDocument)) {
          deliverableDocumentService.deleteDeliverableDocument(deliverableDocument.getId());
        }
      }
    }

    if (deliverable.getDocuments() != null) {
      for (DeliverableDocument deliverableDocument : deliverable.getDocuments()) {

        if (deliverableDocument.getId() == null || deliverableDocument.getId() == -1) {
          DeliverableDocument documentSave = new DeliverableDocument();

          documentSave.setActive(true);
          documentSave.setCreatedBy(this.getCurrentUser());
          documentSave.setModifiedBy(this.getCurrentUser());
          documentSave.setActiveSince(new Date());
          documentSave.setModificationJustification("");
          documentSave.setLink(deliverableDocument.getLink());

          Deliverable deliverable = deliverableService.getDeliverableById(deliverableID);
          documentSave.setDeliverable(deliverable);

          deliverableDocumentService.saveDeliverableDocument(documentSave);


        } else {
          boolean hasChanges = false;
          DeliverableDocument documentPrew =
            deliverableDocumentService.getDeliverableDocumentById(deliverableDocument.getId());

          if (!documentPrew.getLink().equals(deliverableDocument.getLink())) {
            hasChanges = true;
            documentPrew.setLink(deliverableDocument.getLink());
          }

          if (hasChanges) {
            documentPrew.setModifiedBy(this.getCurrentUser());
            documentPrew.setActiveSince(new Date());
            deliverableDocumentService.saveDeliverableDocument(documentPrew);
          }

        }

      }
    }

  }

  public void setAreaID(long areaID) {
    this.areaID = areaID;
  }

  public void setDeliverable(Deliverable deliverable) {
    this.deliverable = deliverable;
  }

  public void setDeliverableID(long deliverableID) {
    this.deliverableID = deliverableID;
  }


  public void setDeliverableTypes(List<DeliverableType> deliverableTypes) {
    this.deliverableTypes = deliverableTypes;
  }

  public void setLoggedCenter(ResearchCenter loggedCenter) {
    this.loggedCenter = loggedCenter;
  }

  public void setProgramID(long programID) {
    this.programID = programID;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setProjectID(long projectID) {
    this.projectID = projectID;
  }

  public void setResearchAreas(List<ResearchArea> researchAreas) {
    this.researchAreas = researchAreas;
  }

  public void setResearchPrograms(List<ResearchProgram> researchPrograms) {
    this.researchPrograms = researchPrograms;
  }

  public void setSelectedProgram(ResearchProgram selectedProgram) {
    this.selectedProgram = selectedProgram;
  }

  public void setSelectedResearchArea(ResearchArea selectedResearchArea) {
    this.selectedResearchArea = selectedResearchArea;
  }

  @Override
  public void validate() {
    if (save) {
      validator.validate(this, deliverable, project, selectedProgram, true);
    }
  }


}
