[#ftl]
[#assign title = "Outputs List" /]
[#assign currentSectionString = "program-${actionName?replace('/','-')}-${programID}" /]
[#assign pageLibs = ["datatables.net", "datatables.net-bs","select2"] /]
[#assign customJS = ["${baseUrl}/js/global/usersManagement.js", "${baseUrl}/js/impactPathway/outputList.js", "${baseUrl}/js/global/fieldsValidation.js"] /]
[#assign customCSS = ["${baseUrl}/css/global/customDataTable.css","${baseUrl}/css/impactPathway/outputList.css"] /]
[#assign currentSection = "impactPathway" /]
[#assign currentStage = "outputs" /]

[#assign breadCrumb = [
  {"label":"impactPathway", "nameSpace":"", "action":"topics"},
  {"label":"outputsList", "nameSpace":"", "action":""}
]/]
[#assign leadersName = "leaders"/]
[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/utils.ftl" as utils /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]
[#import "/WEB-INF/views/impactPathway/outputListTemplate.ftl" as outputsList /]
[#--  marlo cluster of activities--]
<section class="marlo-content">
  <div class="container"> 
    [#if researchAreas?has_content]
    <div class="row">
      <div class="col-md-3">
        [#include "/WEB-INF/views/impactPathway/menu-impactPathway.ftl" /]
      </div>
      <div class="col-md-9">
        [#-- Section Messages --]
        [#include "/WEB-INF/views/impactPathway/messages-impactPathway.ftl" /]
        [#-- Impact pathway sub menu --]
        [#include "/WEB-INF/views/impactPathway/submenu-impactPathway.ftl" /]
        
        [#if researchTopics?has_content]
        
        <span id="programSelected" class="hidden">${selectedProgram.id}</span>
        
        [#-- Research Topic List --]
          <div class="simpleBox col-md-12">
            <label for="">Research Topics:<span class="red">*</span></label>
            <select name="researchTopics" id="researchTopics">
              <option value="-1" >Select an option</option>
              
                [#list researchTopics as researchTopic]
                  <option value="${researchTopic.id}"[#if (selectedResearchTopic.id)?has_content && (selectedResearchTopic.id== researchTopic.id)] selected="selected"[/#if]] >${researchTopic.researchTopic}</option>
                [/#list]
              
            </select>
          </div>
          
        [#if outcomes?has_content]
        [#-- Outcome List --]
          <div class="simpleBox col-md-12">
            <label for="">Select Outcome:<span class="red">*</span></label>
            <select name="outcomes" id="outcomes">
              <option value="-1" >Select an option</option>
              
                [#list outcomes as outcome]
                  <option value="${outcome.id}"[#if (selectedResearchOutcome.id)?has_content && (selectedResearchOutcome.id== outcome.id)] selected="selected"[/#if]] >${outcome.description}</option>
                [/#list]
              
            </select>
          </div>  
         
        
        [@s.form action=actionName enctype="multipart/form-data" ]
        
          [#-- Program Title --]
          <div class="col-md-12">
            <h3 class="subTitle headTitle outcomeListTitle">${selectedProgram.name} - ALL Outputs</h3>
            <hr />
          </div><div class="clearfix"></div>
          
          
   
          
            [#-- Outcomes Table --]
            [#if outputs?has_content]
            <div style="">[@outputsList.outputsList outputs=outputs canValidate=true canEdit=canEdit namespace="/impactPathway" defaultAction="${(centerSession)!}/outputs"/]</div>
            [#else]
              <div class="notOutcome">
              There are NO OUTPUTS added to "<b>${selectedResearchOutcome.description}</b>" as of yet. [#if canEdit] If you want to add a new outcome, please click on the button below: [/#if]
              </div>
            [/#if]
            <br>
            [#-- Add Outcome button --]
            [#if canEdit]            
              [#if outputs?has_content]
                <div class="text-right">
                <div class="addOutcome button-blue"><a  href="[@s.url namespace="/${currentSection}" action='${(centerSession)!}/addNewOutput'] [@s.param name="programID"]${selectedProgram.id}[/@s.param] [@s.param name="outcomeID"]${selectedResearchOutcome.id}[/@s.param][/@s.url]">
                  <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span> [@s.text name="form.buttons.addOutput" /]
                </a></div>
                </div>
              [#else]
                <div class="text-center">
                <div class="addOutcome button-blue"><a  href="[@s.url namespace="/${currentSection}" action='${(centerSession)!}/addNewOutput'] [@s.param name="programID"]${selectedProgram.id}[/@s.param] [@s.param name="outcomeID"]${selectedResearchOutcome.id}[/@s.param][/@s.url]">
                  <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span> [@s.text name="form.buttons.addOutput" /]
                </a></div>
                </div>
              [/#if]
           [/#if]          
          [/@s.form]
        [#else]
         <p class="text-center borderBox inf">Before completing this section, please add at least one Outcome by <a href="[@s.url action='${centerSession}/outcomesList'][@s.param name="programID" value=programID /][@s.param name="topicID" value=selectedResearchTopic.id /][@s.param name="edit" value="true"/][/@s.url]">clicking here</a></p> 
        [/#if]
        [#else]
         <p class="text-center borderBox inf">Before completing this section, please add at least one Research Topic by <a href="[@s.url action='${centerSession}/researchTopics'][@s.param name="programID" value=programID /][@s.param name="edit" value="true"/][/@s.url]">clicking here</a></p> 
        [/#if]
      </div>
    </div>
    [#else]
     <p class="text-center borderBox inf">Program impacts are not available in for the selected research area</p>
    [/#if]
  </div>
</section>

[@customForm.confirmJustificationOut action="deleteOutput.do" namespace="/${currentSection}" title="Remove Output" /]

[#include "/WEB-INF/global/pages/footer.ftl" /]


