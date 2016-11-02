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

/**
 * 
 */
package org.cgiar.ccafs.marlo.action.impactpathway;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConfig;
import org.cgiar.ccafs.marlo.data.model.ResearchArea;
import org.cgiar.ccafs.marlo.data.model.ResearchCenter;
import org.cgiar.ccafs.marlo.data.model.ResearchLeader;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.model.ResearchTopic;
import org.cgiar.ccafs.marlo.data.model.User;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.data.service.IProgramService;
import org.cgiar.ccafs.marlo.data.service.IResearchAreaService;
import org.cgiar.ccafs.marlo.data.service.IResearchTopicService;
import org.cgiar.ccafs.marlo.data.service.IUserService;
import org.cgiar.ccafs.marlo.security.Permission;
import org.cgiar.ccafs.marlo.utils.APConstants;
import org.cgiar.ccafs.marlo.validation.impactpathway.ResearchTopicsValidator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;


/**
 * This action handles the research topics
 * Modified by @author nmatovu last on Oct 3, 2016
 */
public class ResearchTopicsAction extends BaseAction {

  private static final long serialVersionUID = -3428452128710971531L;

  // Services (Managers)
  private ICenterService centerService;
  private IProgramService programService;
  private IResearchAreaService researchAreaService;
  private IResearchTopicService researchTopicService;
  private IUserService userService;

  private ResearchTopicsValidator validator;
  // Local Variables
  private ResearchCenter loggedCenter;
  private List<ResearchArea> researchAreas;
  private List<ResearchTopic> researchTopics;
  private List<ResearchProgram> researchPrograms;
  private ResearchArea selectedResearchArea;
  private ResearchProgram selectedProgram;
  private long programID;
  private long areaID;

  @Inject
  public ResearchTopicsAction(APConfig config, ICenterService centerService, IProgramService programService,
    IResearchAreaService researchAreaService, IResearchTopicService researchTopicService, IUserService userService,
    ResearchTopicsValidator validator) {
    super(config);
    this.centerService = centerService;
    this.programService = programService;
    this.researchAreaService = researchAreaService;
    this.researchTopicService = researchTopicService;
    this.userService = userService;
    this.validator = validator;
  }

  public long getAreaID() {
    return areaID;
  }

  public ResearchCenter getLoggedCenter() {
    return loggedCenter;
  }

  public long getProgramID() {
    return programID;
  }


  public List<ResearchArea> getResearchAreas() {
    return researchAreas;
  }


  public List<ResearchProgram> getResearchPrograms() {
    return researchPrograms;
  }


  public List<ResearchTopic> getResearchTopics() {
    return researchTopics;
  }


  public ResearchProgram getSelectedProgram() {
    return selectedProgram;
  }


  public ResearchArea getSelectedResearchArea() {
    return selectedResearchArea;
  }

  @Override
  public void prepare() throws Exception {
    areaID = -1;
    programID = -1;

    loggedCenter = (ResearchCenter) this.getSession().get(APConstants.SESSION_CENTER);
    loggedCenter = centerService.getCrpById(loggedCenter.getId());

    researchAreas = new ArrayList<>(
      loggedCenter.getResearchAreas().stream().filter(ra -> ra.isActive()).collect(Collectors.toList()));

    Collections.sort(researchAreas, (ra1, ra2) -> ra1.getId().compareTo(ra2.getId()));

    if (researchAreas != null) {

      try {
        areaID = Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.CENTER_AREA_ID)));
      } catch (Exception e) {
        try {
          programID = Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.CENTER_PROGRAM_ID)));
        } catch (Exception ex) {
          User user = userService.getUser(this.getCurrentUser().getId());

          List<ResearchLeader> userLeads = new ArrayList<>(user.getResearchLeaders().stream()
            .filter(rl -> rl.isActive() && rl.getType().getId() == 6).collect(Collectors.toList()));

          if (!userLeads.isEmpty()) {
            programID = userLeads.get(0).getResearchProgram().getId();
          } else {
            if (!researchAreas.isEmpty()) {
              ResearchProgram rp = researchAreas.get(0).getResearchPrograms().stream().filter(r -> r.isActive())
                .collect(Collectors.toList()).get(0);
              programID = rp.getId();
            }
          }
        }
      }

      if (areaID != -1 && programID == -1) {
        selectedResearchArea = researchAreaService.find(areaID);
        researchPrograms = new ArrayList<>(
          selectedResearchArea.getResearchPrograms().stream().filter(rp -> rp.isActive()).collect(Collectors.toList()));
        Collections.sort(researchPrograms, (rp1, rp2) -> rp1.getId().compareTo(rp2.getId()));
        if (researchPrograms != null) {
          try {
            programID = Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.CENTER_PROGRAM_ID)));
          } catch (Exception e) {
            User user = userService.getUser(this.getCurrentUser().getId());

            List<ResearchLeader> userLeads = new ArrayList<>(
              user.getResearchLeaders().stream().filter(rl -> rl.isActive()).collect(Collectors.toList()));

            if (!userLeads.isEmpty()) {
              programID = userLeads.get(0).getResearchProgram().getId();
            } else {
              if (!researchPrograms.isEmpty()) {
                programID = researchPrograms.get(0).getId();
              }
            }
          }
        }

        if (programID != -1) {
          selectedProgram = programService.getProgramById(programID);

          if (selectedProgram != null) {
            if (selectedProgram.getResearchTopics() != null) {
              researchTopics = new ArrayList<>(
                selectedProgram.getResearchTopics().stream().filter(rt -> rt.isActive()).collect(Collectors.toList()));
            }
          }

        }
      } else {
        if (programID != -1) {

          selectedProgram = programService.getProgramById(programID);
          areaID = selectedProgram.getResearchArea().getId();
          selectedResearchArea = researchAreaService.find(areaID);

          researchPrograms = new ArrayList<>(selectedResearchArea.getResearchPrograms().stream()
            .filter(rp -> rp.isActive()).collect(Collectors.toList()));
          if (selectedProgram != null) {
            if (selectedProgram.getResearchTopics() != null) {
              researchTopics = new ArrayList<>(
                selectedProgram.getResearchTopics().stream().filter(rt -> rt.isActive()).collect(Collectors.toList()));
            }
          }
        }
      }
    }


    String params[] = {loggedCenter.getAcronym(), selectedResearchArea.getId() + "", selectedProgram.getId() + ""};
    this.setBasePermission(this.getText(Permission.RESEARCH_PROGRAM_BASE_PERMISSION, params));

    if (this.isHttpPost()) {
      if (researchAreas != null) {
        researchAreas.clear();
      }
      if (researchPrograms != null) {
        researchPrograms.clear();
      }
      if (researchTopics != null) {
        researchTopics.clear();
      }
    }
  }

  @Override
  public String save() {
    if (this.hasPermission("*")) {

      List<ResearchTopic> researchTopicsPrew;

      selectedProgram = programService.getProgramById(programID);

      if (selectedProgram.getResearchTopics() != null) {
        researchTopicsPrew =
          selectedProgram.getResearchTopics().stream().filter(rt -> rt.isActive()).collect(Collectors.toList());

        for (ResearchTopic researchTopic : researchTopicsPrew) {
          if (!researchTopics.contains(researchTopic)) {
            researchTopicService.deleteResearchTopic(researchTopic.getId());
          }
        }
      }


      for (ResearchTopic researchTopic : researchTopics) {
        if (researchTopic.getId() == null || researchTopic.getId() == -1) {
          ResearchTopic newResearchTopic = new ResearchTopic();
          newResearchTopic.setActive(true);
          newResearchTopic.setActiveSince(new Date());
          newResearchTopic.setCreatedBy(this.getCurrentUser());
          newResearchTopic.setResearchTopic(researchTopic.getResearchTopic());
          newResearchTopic.setResearchProgram(selectedProgram);

          researchTopicService.saveResearchTopic(newResearchTopic);
        } else {
          ResearchTopic researchTopicPrew = researchTopicService.getResearchTopicById(researchTopic.getId());
          if (!researchTopicPrew.getResearchTopic().equals(researchTopic.getResearchTopic())) {

            researchTopicPrew.setResearchTopic(researchTopic.getResearchTopic());
            researchTopicPrew.setModifiedBy(this.getCurrentUser());
            researchTopicPrew.setModificationJustification("Modified on " + new Date().toString());

            researchTopicService.saveResearchTopic(researchTopicPrew);
          }
        }

      }

      Collection<String> messages = this.getActionMessages();

      if (!this.getInvalidFields().isEmpty()) {
        this.setActionMessages(null);

        List<String> keys = new ArrayList<String>(this.getInvalidFields().keySet());
        for (String key : keys) {
          this.addActionMessage(key + ": " + this.getInvalidFields().get(key));
        }

      } else {
        this.addActionMessage("message:" + this.getText("saving.saved"));
      }

      messages = this.getActionMessages();

      return SUCCESS;
    } else {
      this.setActionMessages(null);
      return NOT_AUTHORIZED;
    }

  }

  public void setAreaID(long areaID) {
    this.areaID = areaID;
  }

  public void setLoggedCenter(ResearchCenter loggedCenter) {
    this.loggedCenter = loggedCenter;
  }

  public void setProgramID(long programID) {
    this.programID = programID;
  }

  public void setResearchAreas(List<ResearchArea> researchAreas) {
    this.researchAreas = researchAreas;
  }

  public void setResearchPrograms(List<ResearchProgram> researchPrograms) {
    this.researchPrograms = researchPrograms;
  }

  public void setResearchTopics(List<ResearchTopic> researchTopics) {
    this.researchTopics = researchTopics;
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
      validator.validate(this, researchTopics, selectedProgram, true);
    }
  }
}
