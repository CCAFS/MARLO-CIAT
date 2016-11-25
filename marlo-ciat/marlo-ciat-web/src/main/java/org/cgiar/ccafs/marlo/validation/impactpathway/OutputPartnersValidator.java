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

package org.cgiar.ccafs.marlo.validation.impactpathway;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.data.model.ResearchOutput;
import org.cgiar.ccafs.marlo.data.model.ResearchOutputPartner;
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
public class OutputPartnersValidator extends BaseValidator {


  @Inject
  public OutputPartnersValidator() {
    // TODO Auto-generated constructor stub
  }

  public void validate(BaseAction baseAction, ResearchOutput output, ResearchProgram selectedProgram, boolean saving) {
    baseAction.setInvalidFields(new HashMap<>());

    // TODO validator autosave file

    if (!baseAction.getFieldErrors().isEmpty()) {
      baseAction.addActionError(baseAction.getText("saving.fields.required"));
    }

    if (output.getPartners() != null) {
      if (output.getPartners().size() == 0) {
        this.addMissingField("partners");
        this.addMessage(baseAction.getText("output.action.partner.required"));
        baseAction.getInvalidFields().put("list-output.partners",
          baseAction.getText(InvalidFieldsMessages.EMPTYLIST, new String[] {"partners"}));
      }
    } else {
      this.addMissingField("partners");
      this.addMessage(baseAction.getText("output.action.partner.required"));
      baseAction.getInvalidFields().put("list-output.partners",
        baseAction.getText(InvalidFieldsMessages.EMPTYLIST, new String[] {"partners"}));
    }


    for (int i = 0; i < output.getPartners().size(); i++) {
      ResearchOutputPartner researchOutputPartner = output.getPartners().get(i);
      this.validateOutputPartner(baseAction, researchOutputPartner, i);
    }


    this.saveMissingFields(selectedProgram, output, "outputsPartners");
  }

  public void validateOutputPartner(BaseAction baseAction, ResearchOutputPartner partner, int i) {

    List<String> params = new ArrayList<String>();
    params.add(String.valueOf(i + 1));

    if (partner.getUsers() != null) {
      if (partner.getUsers().size() == 0) {
        this.addMessage(baseAction.getText("output.action.partner.user", params));
        baseAction.getInvalidFields().put("list-output.partners[" + i + "]", InvalidFieldsMessages.EMPTYLIST);
      }
    } else {
      this.addMessage(baseAction.getText("output.action.partner.user", params));
      baseAction.getInvalidFields().put("list-output.partners[" + i + "]", InvalidFieldsMessages.EMPTYLIST);
    }
  }

}
