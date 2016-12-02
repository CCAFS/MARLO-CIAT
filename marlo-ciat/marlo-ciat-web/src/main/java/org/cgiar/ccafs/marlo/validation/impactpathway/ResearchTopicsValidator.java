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
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.model.ResearchTopic;
import org.cgiar.ccafs.marlo.utils.InvalidFieldsMessages;
import org.cgiar.ccafs.marlo.validation.BaseValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.inject.Inject;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class ResearchTopicsValidator extends BaseValidator {

  @Inject
  public ResearchTopicsValidator() {
    // TODO Auto-generated constructor stub
  }

  public void validate(BaseAction baseAction, List<ResearchTopic> researchTopics, ResearchProgram selectedProgram,
    boolean saving) {

    baseAction.setInvalidFields(new HashMap<>());

    // TODO validator autosave file

    if (!baseAction.getFieldErrors().isEmpty()) {
      baseAction.addActionError(baseAction.getText("saving.fields.required"));
    }

    if (researchTopics.size() == 0) {
      this.addMessage(baseAction.getText("researchTopic.action.required"));
      baseAction.getInvalidFields().put("list-researchTopics",
        baseAction.getText(InvalidFieldsMessages.EMPTYLIST, new String[] {"researchTopics"}));
    }

    for (int i = 0; i < researchTopics.size(); i++) {
      ResearchTopic researchTopic = researchTopics.get(i);
      this.validateResearchTopic(baseAction, researchTopic, i);
    }

    this.saveMissingFields(selectedProgram, "researchTopic");

  }

  public void validateResearchTopic(BaseAction baseAction, ResearchTopic researchTopic, int i) {

    List<String> params = new ArrayList<String>();
    params.add(String.valueOf(i + 1));

    if (!this.isValidString(researchTopic.getResearchTopic())
      && this.wordCount(researchTopic.getResearchTopic()) <= 150) {
      this.addMessage(baseAction.getText("researchTopic.action.description.required", params));
      baseAction.getInvalidFields().put("input-researchTopics[" + i + "].researchTopic",
        InvalidFieldsMessages.EMPTYFIELD);
    }

  }

}
