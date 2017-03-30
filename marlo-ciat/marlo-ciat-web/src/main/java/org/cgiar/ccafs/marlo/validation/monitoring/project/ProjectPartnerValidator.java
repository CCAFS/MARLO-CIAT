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

package org.cgiar.ccafs.marlo.validation.monitoring.project;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.data.model.Project;
import org.cgiar.ccafs.marlo.data.model.ProjectPartner;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.utils.InvalidFieldsMessages;
import org.cgiar.ccafs.marlo.validation.BaseValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.inject.Inject;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class ProjectPartnerValidator extends BaseValidator {

  @Inject
  public ProjectPartnerValidator() {
    // TODO Auto-generated constructor stub
  }

  public void validate(BaseAction baseAction, Project project, ResearchProgram selectedProgram, boolean saving) {

    baseAction.setInvalidFields(new HashMap<>());

    // TODO Autosave Draft validator.

    if (!baseAction.getFieldErrors().isEmpty()) {
      baseAction.addActionError(baseAction.getText("saving.fields.required"));
    }

    if (project.getPartners() != null) {
      if (project.getPartners().size() == 0) {
        this.addMessage(baseAction.getText("output.action.partner.required"));
        baseAction.getInvalidFields().put("list-output.partners",
          baseAction.getText(InvalidFieldsMessages.EMPTYLIST, new String[] {"partners"}));
      }
    } else {
      this.addMessage(baseAction.getText("output.action.partner.required"));
      baseAction.getInvalidFields().put("list-output.partners",
        baseAction.getText(InvalidFieldsMessages.EMPTYLIST, new String[] {"partners"}));
    }


    for (int i = 0; i < project.getPartners().size(); i++) {
      ProjectPartner partner = project.getPartners().get(i);
      this.validateOutputPartner(baseAction, partner, i);
    }


    this.saveMissingFields(selectedProgram, project, "projectPartners");

  }

  public void validateOutputPartner(BaseAction baseAction, ProjectPartner partner, int i) {

    List<String> params = new ArrayList<String>();
    params.add(String.valueOf(i + 1));

    if (partner.getUsers() != null) {
      if (partner.getUsers().size() == 0) {
        this.addMessage(baseAction.getText("output.action.partner.user"));
        baseAction.getInvalidFields().put("list-project.partners[" + i + "].users",
          baseAction.getText(InvalidFieldsMessages.EMPTYLIST, new String[] {"users"}));

      }
    } else {
      this.addMessage(baseAction.getText("output.action.partner.user"));
      baseAction.getInvalidFields().put("list-project.partners[" + i + "].users",
        baseAction.getText(InvalidFieldsMessages.EMPTYLIST, new String[] {"users"}));


    }
  }


}
