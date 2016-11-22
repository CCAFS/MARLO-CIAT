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
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.utils.InvalidFieldsMessages;
import org.cgiar.ccafs.marlo.validation.BaseValidator;

import java.util.HashMap;

import com.google.inject.Inject;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class OutputsValidator extends BaseValidator {

  @Inject
  public OutputsValidator() {
    // TODO Auto-generated constructor stub
  }

  public void validate(BaseAction baseAction, ResearchOutput output, ResearchProgram selectedProgram, boolean saving) {
    baseAction.setInvalidFields(new HashMap<>());

    // TODO validator autosave file

    if (!baseAction.getFieldErrors().isEmpty()) {
      baseAction.addActionError(baseAction.getText("saving.fields.required"));
    }

    this.validateOutput(baseAction, output);

    this.saveMissingFields(selectedProgram, output, "outputs");
  }

  public void validateOutput(BaseAction baseAction, ResearchOutput output) {

    if (!this.isValidString(output.getTitle()) && this.wordCount(output.getTitle()) <= 100) {
      this.addMessage(baseAction.getText("output.action.title.required"));
      baseAction.getInvalidFields().put("input-output.title", InvalidFieldsMessages.EMPTYFIELD);
    }
  }


}
