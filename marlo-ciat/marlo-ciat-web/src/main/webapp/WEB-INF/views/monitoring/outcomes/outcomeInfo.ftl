[#ftl]
[#assign title = "MiLE outcome information" /]
[#assign currentSectionString = "${actionName?replace('/','-')}" /]
[#assign pageLibs = ["select2"] /]
[#assign customJS = ["" ] /]
[#assign customCSS = [""] /]
[#assign currentSection = "monitoring" /]


[#assign breadCrumb = [
  {"label":"outcomesList", "nameSpace":"/monitoring", "action":"${(centerSession)!}/monitoringOutcomesList"}
]/]

[#assign editable = true /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

<div class="container helpText viewMore-block">
  <div class="helpMessage infoText">
    [#--  <img class="col-md-2" src="${baseUrl}/images/global/icon-help.jpg" />--]
    <p class="col-md-10"> [@s.text name=""][/@s.text] </p>
  </div> 
  <div style="display:none" class="viewMore closed"></div>
</div>
<span id="programSelected" class="hidden">${selectedProgram.id}</span>

<section class="container">
  <article class="row" id="mainInformation">
    <div class="col-md-12">
      [#include "/WEB-INF/views/monitoring/outcomes/submenu-outcomes.ftl" /]
      
      [@s.form action=actionName method="POST" enctype="multipart/form-data" cssClass=""]
      [#-- Outcomes List --]
      <h3 class="headTitle text-center"></h3>
      <div class="simpleBox col-md-12">
        <div class="col-md-4">
          <label for="">Research topic:  </label>
          <p>${selectedResearchTopic.researchTopic}</p>
        </div>
        <div class="col-md-4">
          <label for="">Outcome name:  </label>
          <p>${(outcome.description)!}</p>
        </div>
        <div class="col-md-4">
          <label for="">Expected for ${(outcome.targetYear)!"null"}:  </label>
          <p>${(outcome.value)!"Not Applicable"}</p>
        </div>
      </div>
    <div class="clearfix"></div>
    <div class="">
      [#-- Year Tabs --]
      <ul class="nav nav-tabs" role="tablist">
        [#list outcome.monitorings as year]
          <li class="[#if year.year == action.getYear()]active[/#if]"><a href="#outcomeYear-${year.year}" role="tab" data-toggle="tab">${year.year}[#if year.year == action.getYear()] <span class="red">*</span> [/#if] </a></li>
        [/#list]
      </ul>
      [#-- Years Content --]
      <div class="tab-content col-md-12" style="border: 1px solid #e7e7e7;">
      <br />
        [#list outcome.monitorings as outcome]
        
          <div role="tabpanel" class="tab-pane [#if outcome.year == action.getYear()]active[/#if]" id="outcomeYear-${outcome.year}">
          [#if outcome_index==0]
          <div class="col-md-2">
            [@customForm.input name="outcome.baseline" i18nkey="Initial Baseline" required=true editable=true /]
          </div>
          <div class="clearfix"></div>
          [/#if]
          <div class="col-md-12">
            <h5 class="sectionSubTitle">Milestones/ progress towards your outcome target contribution:</h5>
            <div class="note left">
              When writing your narrative, please consider if you have achieved changes in Attitudes, Skills, and Knowledge. Key words to express progress  measurement that may apply to your outcome may include: Change of Practice→ Use, Adaptation, Adoption → Sustainable use, Scaling out / Scaling up
            </div>
            <br />
            [#-- MILESTONE LIST --]
            <div class="milestoneList simpleBox">
            [#if outcome.milestones?has_content]
              [#list outcome.milestones as milestone]
                [@milestoneMacro milestone=milestone name="outcome.monitorings[${outcome_index}].milestones" index=milestone_index /]
              [/#list]
            [#else]
              <p class="message text-center">[@s.text name="outcome.milestones.emptyMessage"/]</p>
            [/#if]
            </div>
            <div class="text-center">
              <div class="button-green addMilestone"><span class="glyphicon glyphicon-plus-sign"></span>[@s.text name="Add a milestone" /]</div>
            </div>
          </div>
            <br />
            [#-- EVIDENCE OF USE --]
            <div class="col-md-12 form-group">
              <h5 class="sectionSubTitle">Evidence of use:</h5>
                [#-- EVIDENCE LIST --]
              <div class="evidenceList simpleBox">
              [#if outcome.evidences?has_content]
                [#list outcome.evidences as evidence]
                  [@evidenceMacro milestone=evidence name="outcome.monitorings[${outcome_index}].evidence" index=evidence_index /]
                [/#list]
              [#else]
                <p class="message text-center">[@s.text name="There are not Evicences associated to this outcome as of yet"/]</p>
              [/#if]
              </div>
              <div class="text-center">
                <div class="button-green addEvidence"><span class="glyphicon glyphicon-plus-sign"></span>[@s.text name="Add a evidence" /]</div>
              </div>
            </div>
          
          </div>
        [/#list] 
      </div>
    </div>
      
         
      <div class="clearfix"></div>
          [#-- Section Buttons & hidden inputs--]
          [#include "/WEB-INF/views/impactPathway/buttons-impactPathway-outcome.ftl" /]
    </div>
    [/@s.form] 
  </article>
</section>


[#include "/WEB-INF/global/pages/footer.ftl"]

[#macro milestoneMacro milestone name index isTemplate=false]
  [#assign milestoneCustomName = "${name}[${index}]" /]
  <div id="milestone-${isTemplate?string('template', index)}" class="milestone borderBox" style="display:${isTemplate?string('none','block')}">
    <div class="leftHead green sm">
      <span class="index">${index+1}</span>
      <span class="elementId">[@s.text name="outcome.milestone.index.title"/]</span>
    </div>
     <input type="hidden" class="mileStoneId" name="${milestoneCustomName}.researchMilestone.id" value="${(milestone.researchMilestone.id)!}"/>
    [#-- Remove Button --]
    [#if editable=!editable]
      <div class="removeMilestone removeElement sm" title="Remove Milestone"></div>
    [/#if]
    
    [#-- Milestone Statement --]
    <div class="form-group" style="margin-top: 15px;">
      [@customForm.textArea name="${milestoneCustomName}.researchMilestone.title" i18nkey="outcome.milestone.index.statement" required=true className="milestone-statement limitWords-50" editable=false /]
    </div>
    <div class="row form-group target-block">      
      [#-- Target Unit --]
      <div class="col-md-3">
        [@customForm.input name="${milestoneCustomName}.achievedValue" i18nkey="Achieved value" required=false editable=editable /]
      </div>
      
      <div class="col-md-3 col-md-offset-3">
        [@customForm.input name="${milestoneCustomName}.researchMilestone.targetYear" i18nkey="Expected completion year" required=false editable=false /]
      </div>
      
      
    </div>
    [#-- Milestone narrative --]
    <div class="form-group" style="margin-top: 15px;">
      [@customForm.textArea name="${milestoneCustomName}.narrative" i18nkey="Narrative for your level of progress on this outcome milestone" required=true className="milestone-statement limitWords-100" editable=editable /]
    </div> 
    
  </div>
[/#macro]

[#macro evidenceMacro milestone name index isTemplate=false]
  [#assign evidenceCustomName = "${name}[${index}]" /]
  <div  id="evidence-${isTemplate?string('template', index)}" class="evidence simpleBox" style="display:${isTemplate?string('none','block')}" >
    <div class="removeMilestone removeElement sm" title="Remove evidence"></div>
    [@customForm.input name="${evidenceCustomName}.evidence" i18nkey="Evidence" placeholder="Link" required=true editable=editable /]
  </div>
[/#macro]
