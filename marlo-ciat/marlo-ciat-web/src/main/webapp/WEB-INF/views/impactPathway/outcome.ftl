[#ftl]
[#assign title = "Outcome" /]
[#assign currentSectionString = "program-${actionName?replace('/','-')}-${programID}" /]
[#assign pageLibs = ["datatables.net", "datatables.net-bs","select2"] /]
[#assign customJS = ["${baseUrl}/js/global/usersManagement.js", "${baseUrl}/js/impactPathway/outcome.js", "${baseUrl}/js/global/fieldsValidation.js"] /]
[#assign customCSS = ["${baseUrl}/css/global/customDataTable.css","${baseUrl}/css/impactPathway/outcomeList.css"] /]
[#assign currentSection = "impactPathway" /]
[#assign currentStage = "outcomes" /]

[#assign breadCrumb = [
  {"label":"impactPathway", "nameSpace":"", "action":"topics"},
  {"label":"outcomesList", "nameSpace":"", "action":"outcomesList"},
  {"label":"outcome", "nameSpace":"", "action":""}
]/]
[#assign leadersName = "leaders"/]
[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/utils.ftl" as utils /]
[#import "/WEB-INF/views/impactPathway/outcomeListTemplate.ftl" as outcomesList /]
[#--  marlo cluster of activities--]
<section class="marlo-content">
  <div class="container"> 
    
    <div class="row">
      <div class="col-md-3">
        [#include "/WEB-INF/views/impactPathway/menu-impactPathway.ftl" /]
      </div>
      <div class="col-md-9">
        [#-- Section Messages --]
        [#include "/WEB-INF/views/impactPathway/messages-impactPathway.ftl" /]
        [#-- Impact pathway sub menu --]
        [#include "/WEB-INF/views/impactPathway/submenu-impactPathway.ftl" /]

        <span id="programSelected" class="hidden">${(selectedProgram.id)!}</span>
        
        <div class="simpleBox col-md-12">
            <label for="">Research Topics:<span class="red">*</span></label>
            <select name="researchTopics" id="researchTopics">
              <option value="-1" >Select an option</option>
              
                [#list researchTopics as researchTopic]
                  <option value="${researchTopic.id}"[#if (selectedResearchTopic.id)?has_content && (selectedResearchTopic.id== researchTopic.id)] selected="selected"[/#if]] >${researchTopic.researchTopic}</option>
                [/#list]
              
            </select>
         </div>
        
        <br />
        <br />
        
        [#-- Back --]
        <small class="pull-right">
          <a href="[@s.url action='${centerSession}/outcomesList'][@s.param name="programID" value=programID /][/@s.url]">
            <span class="glyphicon glyphicon-circle-arrow-left"></span> Back to the outcomes list
          </a>
        </small>
        
        <div class="clearfix"></div>
        
        [@s.form action=actionName enctype="multipart/form-data" ]
        
        <div class="borderBox">
          <h5 class="sectionSubTitle"> Outcome Information</h5>
          [#assign outcomeCustomName= "outcome" /]
          
          [#-- Research impact --]
          <div class="form-group">
            [@customForm.select name="${outcomeCustomName}.researchImpact"  i18nkey="outcome.researchImpact" listName="researchImpactsList"  required=true  className=""  disabled=!editable/]
          </div>
          [#-- Outcome Statement --]
          <div class="form-group">
            [@customForm.textArea name="${outcomeCustomName}.description"  i18nkey="outcome.statement" required=true className="outcome-statement limitWords-100" editable=editable /]
          </div>
          <div class="row form-group target-block">
            [#-- Target Year --]
            <div class="col-md-4">[@customForm.input name="${outcomeCustomName}.targetYear" value="${(outcome.targetYear)!2022}" type="text" i18nkey="outcome.year"  placeholder="outcome.inputTargetYear.placeholder" className="targetYear outcomeYear" required=true editable=editable /]</div>
            [#-- Target Unit --]
            <div class="col-md-4">
              [@customForm.select name="${outcomeCustomName}.srfTargetUnit.id" i18nkey="outcome.selectTargetUnit"  placeholder="outcome.selectTargetUnit.placeholder" className="targetUnit" listName="targetUnitList" editable=editable  /]
              [#-- If you dont find the target unit in the list, please add a new one clicking here --]
              [#--  --if editable]<div class="addOtherTargetUnit text-center"><a href="#">([@s.text name = "outcomes.addNewTargetUnit" /])</a></div>[/#if --]
            </div>
            [#-- Target Value --]
            [#assign showTargetValue =  (outcome??) && (outcome.srfTargetUnit??) && (outcome.srfTargetUnit.id??) && (outcome.srfTargetUnit.id != -1) /]
            <div class="col-md-4 targetValue-block" style="display:${showTargetValue?string('block', 'none')}">
              [@customForm.input name="${outcomeCustomName}.value" type="text" i18nkey="outcome.targetValue" placeholder="outcome.inputTargetValue.placeholder" className="targetValue" required=true editable=editable /]
            </div> 
          </div>   
          <br />
          <br />
          [#-- Outcome Milestones List --]
          <h5 class="sectionSubTitle"> Outcome Milestones</h5>
          <div class="milestones-list" listname="${outcomeCustomName}.milestones">
          [#if outcome.milestones?has_content]
            [#list outcome.milestones as milestone]
              [@milestoneMacro milestone=milestone name="${outcomeCustomName}.milestones" index=milestone_index /]
            [/#list]
          [#else]
            <p class="message text-center">[@s.text name="outcome.milestones.emptyMessage"/]</p>
          [/#if]
          </div>
          [#-- Add Milestone Button --]
          [#if editable]
          <div class="text-right">
            <div class="addMilestone button-blue"><span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span> [@s.text name="form.buttons.addMilestone"/]</div>
          </div>
          [/#if]
          
        </div>
          
        [#-- Section Buttons--]
        [#include "/WEB-INF/views/impactPathway/buttons-impactPathway.ftl" /]
        
        [#-- Hidden inputs --]
        <input type="hidden" name="outcomeID" value="${outcomeID}" />
          
        [/@s.form]
        
      </div>
    </div>
    
  </div>
</section>

[#-- Milestone Template --]
[@milestoneMacro milestone={} name="project.milestones" index=-1 isTemplate=true /]

[#include "/WEB-INF/global/pages/footer.ftl" /]


[#macro milestoneMacro milestone name index isTemplate=false]
  [#assign milestoneCustomName = "${name}[${index}]" /]
  <div id="milestone-${isTemplate?string('template', index)}" class="milestone simpleBox" style="display:${isTemplate?string('none','block')}">
    <div class="leftHead green sm">
      <span class="index">${index+1}</span>
      <span class="elementId">[@s.text name="milestone.index.title"/]</span>
    </div>
     <input type="hidden" class="mileStoneId" name="${milestoneCustomName}.id" value="${(milestone.id)!}"/>
    [#-- Remove Button --]
    [#if editable]
      <div class="removeMilestone removeElement sm" title="Remove Milestone"></div>
    [/#if]
    
    [#-- Milestone Statement --]
    <div class="form-group">
      [@customForm.textArea name="${milestoneCustomName}.title" i18nkey="milestone.statement" required=true className="milestone-statement limitWords-50" editable=editable /]
    </div>
    <div class="row form-group target-block">
      [#-- Target Year --]
      ${(outcome.milestone.targetYears)!}
      <div class="col-md-4">[@customForm.select name="${milestoneCustomName}.year" value="${(milestone.year)!-1}"  i18nkey="milestone.inputTargetYear" listName="milestoneYears"  required=true  className=" targetYear milestoneYear"  disabled=!editable/]</div>
      [#-- Target Unit --]
      <div class="col-md-4">
        [@customForm.select name="${milestoneCustomName}.srfTargetUnit.id"  i18nkey="milestone.selectTargetUnit" placeholder="outcome.selectTargetUnit.placeholder" className="targetUnit" listName="targetUnitList" editable=editable  /]
        [#--  --if editable]<div class="addOtherTargetUnit text-center"><a href="#">([@s.text name = "outcomes.addNewTargetUnit" /])</a></div>[/#if--]
      </div>
      [#-- Target Value --]
      [#local showTargetValue = (milestone.srfTargetUnit??) && (milestone.srfTargetUnit.id??) && (milestone.srfTargetUnit.id != -1) /]
      <div class="col-md-4 targetValue-block" style="display:${showTargetValue?string('block', 'none')}">
        [@customForm.input name="${milestoneCustomName}.value" type="text"  i18nkey="milestone.inputTargetValue" placeholder="milestone.inputTargetValue.placeholder" className="targetValue" required=true editable=editable /]
      </div>
    </div> 
    
  </div>
[/#macro]
