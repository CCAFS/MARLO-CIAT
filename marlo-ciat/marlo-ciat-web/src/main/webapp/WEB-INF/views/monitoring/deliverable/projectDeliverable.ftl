[#ftl]
[#assign title = "Project Deliverable" /]
[#assign currentSectionString = "project-${actionName?replace('/','-')}-${deliverableID}" /]
[#assign pageLibs = ["select2","jsUri"] /]
[#assign customJS = ["${baseUrl}/js/global/fieldsValidation.js","${baseUrl}/js/global/usersManagement.js","${baseUrl}/js/monitoring/deliverable/projectDeliverable.js","${baseUrl}/js/global/autoSave.js"] /]
[#assign customCSS = ["${baseUrl}/css/deliverable/projectDeliverable.css"] /]
[#assign currentSection = "projects" /]
[#assign currentStage = "deliverables" /]


[#assign breadCrumb = [
  {"label":"projectsList", "nameSpace":"/monitoring", "action":"${(centerSession)!}/projectList"},
  {"label":"deliverables", "nameSpace":"/monitoring", "action":"${(centerSession)!}/deliverableList"}
] /]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]

<div class="container helpText viewMore-block">
  <div class="helpMessage infoText">
    [#-- <div  class="removeHelp"><span class="glyphicon glyphicon-remove"></span></div> --]
    <p class="col-md-10"> [@s.text name="projectDescription.help" /] </p>
  </div> 
  <div style="display:none" class="viewMore closed"></div>
</div>

<section class="container">
    <div class="row">
      [#-- Project Menu --]
      <div class="col-md-3">
        [#include "/WEB-INF/views/monitoring/project/menu-projects.ftl" /]
      </div>
      [#-- Project Section Content --]
      <div class="col-md-9">
        [#-- Section Messages --]
        [#--  --include "/WEB-INF/views/projects/messages-projects.ftl" / --]
        [#-- Projects data information --]
        [#include "/WEB-INF/views/monitoring/project/dataInfo-projects.ftl" /]
        <br />
      
        [@s.form action=actionName method="POST" enctype="multipart/form-data" cssClass=""]
          
          [#-- Back --]
            <small class="pull-right">
              <a href="[@s.url action='${centerSession}/deliverableList'][@s.param name="projectID" value=project.id /][/@s.url]">
                <span class="glyphicon glyphicon-circle-arrow-left"></span> Back to the deliverables list
              </a>
            </small>  
          <h3 class="headTitle">[@s.text name="Key deliverable information" /]</h3>
          <div id="projectDeliverable" class="borderBox">
            <div class="form-group row">  
            [#-- Deliverable title --]
              <div class="col-md-12">
                [@customForm.input name="deliverable.name" i18nkey="Title" type="text" disabled=!editable  required=true editable=editable /]
              </div>  
            </div>
            <div class="form-group row">
              [#-- deliverable tpye --]
              <div class="col-md-12">
                [@customForm.select name="deliverable.deliverableType.id"  i18nkey="Type" listName="deliverableTypes" keyFieldName="id"  displayFieldName="name" className="deliverableTypeSelect" value="deliverable.deliverableType.id" /]
              </div>  
            </div>
            <div class="form-group row">  
              [#-- Start Date --]
              <div class="col-md-6">
                [@customForm.input name="deliverable.startDate" i18nkey="Start date" type="text" disabled=!editable  required=true editable=editable /]
              </div> 
              [#-- End Date --]
              <div class="col-md-6">
                [@customForm.input name="deliverable.endDate" i18nkey="End date" type="text" disabled=!editable required=false editable=editable /]
              </div>
              
              
              [#-- Select the cross-cutting dimension(s) to this project? --]
              <div class="form-group col-md-12">
                <label for="">[@customForm.text name="deliverable.crossCuttingDimensions" readText=!editable/] [@customForm.req required=editable/]</label>
                <div class="row">
                  <div class="col-md-12">
                    [#if editable]
                      <label class="checkbox-inline"><input type="checkbox" name="deliverable.deliverableCrosscutingTheme.climateChange"   id="climate"   value="true" [#if (deliverable.deliverableCrosscutingTheme.climateChange)!false ]checked="checked"[/#if] > Climate Change</label>
                      <label class="checkbox-inline"><input type="checkbox" name="deliverable.deliverableCrosscutingTheme.gender"    id="gender"    value="true" [#if (deliverable.deliverableCrosscutingTheme.gender)!false ]checked="checked"[/#if] > Gender</label>
                      <label class="checkbox-inline"><input type="checkbox" name="deliverable.deliverableCrosscutingTheme.youth"    id="youth"    value="true" [#if (deliverable.deliverableCrosscutingTheme.youth)!false ]checked="checked"[/#if] > Youth</label>
                      <label class="checkbox-inline"><input type="checkbox" name="deliverable.deliverableCrosscutingTheme.policiesInstitutions"    id="policies"    value="true" [#if (deliverable.deliverableCrosscutingTheme.policiesInstitutions)!false ]checked="checked"[/#if] > Policies and Institutions</label>
                    [#else]
                      [#if (deliverable.deliverableCrosscutingTheme.climateChange)!false ]<p class="checked"> Climate Change</p>[/#if]
                      [#if (deliverable.deliverableCrosscutingTheme.gender)!false ]<p class="checked"> Gender</p>[/#if]
                      [#if (deliverable.deliverableCrosscutingTheme.youth)!false ]<p class="checked"> Youth</p>[/#if]
                      [#if (deliverable.deliverableCrosscutingTheme.policiesInstitutions)!false ]<p class="checked"> Policies and Institutions</p>[/#if]                      
                    [/#if]
                  </div>
                  <div class="col-md-12">
                    [#if editable]
                      <label class="checkbox-inline"><input type="checkbox" name="deliverable.deliverableCrosscutingTheme.capacityDevelopment" id="capacity" value="true" [#if (deliverable.deliverableCrosscutingTheme.capacityDevelopment)!false ]checked="checked"[/#if] > Capacity Development</label>
                      <label class="checkbox-inline"><input type="checkbox" name="deliverable.deliverableCrosscutingTheme.bigData" id="bigData" value="true" [#if (deliverable.deliverableCrosscutingTheme.bigData)!false ]checked="checked"[/#if] > Big Data</label>
                      <label class="checkbox-inline"><input type="checkbox" name="deliverable.deliverableCrosscutingTheme.impactAssessment" id="impactAssessment" value="true" [#if (deliverable.deliverableCrosscutingTheme.impactAssessment)!false ]checked="checked"[/#if] > Impact Assessment</label>
                      <label class="checkbox-inline"><input type="checkbox" name="deliverable.deliverableCrosscutingTheme.na"       id="na"       value="true" [#if (deliverable.deliverableCrosscutingTheme.na)!false ]checked="checked"[/#if] > N/A</label>
                    [#else]
                      [#if (deliverable.deliverableCrosscutingTheme.capacityDevelopment)!false ]<p class="checked"> Capacity Development</p>[/#if]
                      [#if (deliverable.deliverableCrosscutingTheme.bigData)!false ]<p class="checked"> Big Data</p>[/#if]
                      [#if (deliverable.deliverableCrosscutingTheme.impactAssessment)!false ]<p class="checked"> Impact Assessment</p>[/#if]
                      [#if (deliverable.deliverableCrosscutingTheme.na)!false ]<p class="checked"> N/A</p>[/#if]                     
                    [/#if]
                  </div>
                </div>
                <br />
              </div>
          </div>
          
           [#-- Outputs --]
              <div class="fullPartBlock">      
                <div class="output panel tertiary" listname="deliverable.outputs">
                  <div class="panel-head"><label for="">[@customForm.text name="deliverable.outputs" readText=!editable /]</label></div> 
                  <div class="panel-body"> 
                    <ul id="outputsBlock" class="list outputList">
                    [#if  deliverable.outputs?has_content]  
                      [#list deliverable.outputs as output]
                         [@outputMacro element=output name="deliverable.outputs" index=output_index isTemplate=false/]
                      [/#list] 
                    [#else]
                      <p class="text-center outputInf"> [@s.text name="deliverable.notOutputs" /] </p>  
                    [/#if]  
                    </ul>
                    [#if editable]
                      [@customForm.select name="" label="" disabled=!canEdit i18nkey="" listName="outputs" keyFieldName="id"  displayFieldName="title" className="outputSelect" value="" /]
                    [/#if] 
                  </div>
                </div> 
              </div> 
              
          <label for="">Supporting document(s):<span class="red">*</span></label>
          <div class="borderBox documentList" listname="deliverable.documents">
            [#if deliverable.documents?has_content]
              [#list deliverable.documents as document]
                [@documentMacro element=document name="deliverable.documents"  index=document_index isTemplate=false /]
              [/#list]
            [/#if]
            <p class="text-center inf" style="display:${(deliverable.documents?has_content)?string('none','block')}">[@s.text name="There are not document(s) added yet." /]</p>
          </div>
          <div class="text-right">
            <div class="button-green addDocument"><span class="glyphicon glyphicon-plus-sign"></span>[@s.text name="Add a support document" /]</div>
          </div>
        </div>
      [#-- Section Buttons & hidden inputs--]
          [#include "/WEB-INF/views/monitoring/deliverable/buttons-deliverable.ftl" /]
             
          [/@s.form] 
      
    </div>  
</section>


[@documentMacro element={} name="deliverable.documents"  index=-1 isTemplate=true /]
[@outputMacro element={} name="deliverable.outputs"  index=-1 isTemplate=true /]
  
[#include "/WEB-INF/global/pages/footer.ftl"]


[#macro documentMacro element name index=-1 isTemplate=false]
  [#assign documentCustomName = "${name}[${index}]" /]
  <div id="document-${isTemplate?string('template',(element.id)!)}" class="documents form-group row"  style="display:${isTemplate?string('none','block')}">
    
    <input class="id" type="hidden" name="${documentCustomName}.id" value="${(element.id)!-1}" />
    <div class="col-md-12">
    [#if editable]<div class="removeDocument removeIcon" title="Remove document"></div>[/#if] 
      [@customForm.input name="${documentCustomName}.link" i18nkey="Link" type="text" className="link" disabled=!editable required=true editable=editable /]
    </div>
  </div>
[/#macro]

[#macro outputMacro element name index=-1 isTemplate=false]  
  [#assign outputCustomName = "${name}[${index}]" /]
  <li id="output-${isTemplate?string('template',(element.id)!)}" class="outputs  borderBox expandableBlock row "  style="display:${isTemplate?string('none','block')}">
  <input type="hidden" name="${outputCustomName}.id" value="${(element.id)!}"/>
  <input type="hidden" class="outputId" name="${outputCustomName}.researchOutput.id" value="${(element.researchOutput.id)!}"/>
    [#if editable] [#--&& (isTemplate) --]
      <div class="removeLink">
        <div id="" class="removeOutput removeElement removeLink" title="[@s.text name='deliverable.removeOutput' /]"></div>
      </div>
    [/#if]
    <div class="leftHead">
        <span class="index">O${(element.researchOutput.id)!}</span>
      </div>
    [#-- output Title --]
    <div class="blockTitle closed form-group" style="margin-top:30px;">
      <label for="">Output statement:</label>
      <div class="oStatement">
        [#if element.researchOutput?? && element.researchOutput.title?has_content]
        ${(element.researchOutput.title)!'New output'}
        [#else]
        No Output
        [/#if]
      </div>
        
      <div class="clearfix"></div>
    </div>
    
    <div class="blockContent " style="display:none">
      <div class="form-group">
        <label for="">Research topic:</label>
        <div class="rTopic">${(element.researchOutput.researchOutcome.researchTopic.researchTopic)!}</div>
      </div>
      <div class="form-group">
        <label for="">Outcome:</label>
        <div class="outcome">${(element.researchOutput.researchOutcome.description)!}</div>
      </div>
    </div>
  </li>
[/#macro]
