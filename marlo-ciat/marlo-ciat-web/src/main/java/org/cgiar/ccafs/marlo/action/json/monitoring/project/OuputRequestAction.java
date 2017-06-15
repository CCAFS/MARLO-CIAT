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

package org.cgiar.ccafs.marlo.action.json.monitoring.project;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConfig;
import org.cgiar.ccafs.marlo.data.model.ResearchOutcome;
import org.cgiar.ccafs.marlo.data.model.TopicOutcomes;
import org.cgiar.ccafs.marlo.data.service.IProgramService;
import org.cgiar.ccafs.marlo.data.service.IResearchOutcomeService;
import org.cgiar.ccafs.marlo.utils.APConstants;
import org.cgiar.ccafs.marlo.utils.SendMail;

import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class OuputRequestAction extends BaseAction {


  private static final long serialVersionUID = -1648698670582263338L;


  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(OuputRequestAction.class);

  private SendMail sendMail;
  private IResearchOutcomeService outcomeService;
  private IProgramService programService;
  private List<TopicOutcomes> outcomes;
  private boolean messageSent;
  private Long outcomeID;
  private String outputName;


  @Inject
  public OuputRequestAction(APConfig config, IResearchOutcomeService outcomeService, IProgramService programService,
    SendMail sendMail) {
    super(config);
    this.outcomeService = outcomeService;
    this.programService = programService;
    this.sendMail = sendMail;
  }

  @Override
  public String execute() throws Exception {
    String subject;
    StringBuilder message = new StringBuilder();

    ResearchOutcome outcome = outcomeService.getResearchOutcomeById(outcomeID);

    String outcomeName = outcome.getComposedName();
    String outputName = this.outputName;

    // message subject
    subject = "[MARLO -" + this.getCenterSession().toUpperCase() + "] Output verification - " + "P";
    // Message content
    message.append(this.getCurrentUser().getFirstName() + " " + this.getCurrentUser().getLastName() + " ");
    message.append("(" + this.getCurrentUser().getEmail() + ") ");
    message.append("is requesting to add the following ouput information:");
    message.append("</br></br>");
    message.append("Output Name: ");
    message.append(outputName);
    message.append("</br>");
    message.append("Outcome : ");
    message.append(outcomeName);
    message.append(" </br>");

    try {
      sendMail.send(config.getEmailNotification(), null, config.getEmailNotification(), subject, message.toString(),
        null, null, null, true);
    } catch (Exception e) {

    }
    Collection<String> messages = this.getActionMessages();
    this.addActionMessage("message:" + this.getText("saving.saved"));
    messages = this.getActionMessages();

    messageSent = true;

    return SUCCESS;
  }

  public Long getOutcomeID() {
    return outcomeID;
  }


  public List<TopicOutcomes> getOutcomes() {
    return outcomes;
  }


  public String getOutputName() {
    return outputName;
  }

  public boolean isMessageSent() {
    return messageSent;
  }

  @Override
  public void prepare() throws Exception {

    if (this.getRequest().getParameter(APConstants.OUTCOME_ID) != null) {
      outcomeID = Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.OUTCOME_ID)));
      LOG.info("The user {} load the output request section related to the program {}.",
        this.getCurrentUser().getEmail(), outcomeID);
    }

    if (this.getRequest().getParameter(APConstants.OUTPUT_NAME) != null) {
      outputName = StringUtils.trim(this.getRequest().getParameter(APConstants.OUTPUT_NAME));
      LOG.info("The user {} load the output request section related to the program {}.",
        this.getCurrentUser().getEmail(), outputName);
    }


  }

  public void setMessageSent(boolean messageSent) {
    this.messageSent = messageSent;
  }

  public void setOutcomeID(Long outcomeID) {
    this.outcomeID = outcomeID;
  }

  public void setOutcomes(List<TopicOutcomes> outcomes) {
    this.outcomes = outcomes;
  }


  public void setOutputName(String outputName) {
    this.outputName = outputName;
  }


}