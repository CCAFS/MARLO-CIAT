[#ftl]
[#assign title = "Project Description" /]
[#assign currentSectionString = "project-${actionName?replace('/','-')}-${projectID}" /]
[#assign pageLibs = ["select2"] /]
[#assign customJS = ["${baseUrl}/js/global/fieldsValidation.js""${baseUrl}/js/global/usersManagement.js","${baseUrl}/js/projects/projectDescription.js"] /]
[#assign currentSection = "projects" /]
[#assign currentStage = "description" /]
[#assign editable = true /]

[#assign breadCrumb = [
  {"label":"projectsList", "nameSpace":"/monitoring", "action":"${(centerSession)!}/projectList"},
  {"label":"projectDescription", "nameSpace":"/monitoring", "action":""}
] /]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#-- Search users Interface --]
[#import "/WEB-INF/global/macros/usersPopup.ftl" as usersForm/]

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
        [#include "/WEB-INF/views/monitoring/project/submenu-project.ftl" /]
        [#-- Section Messages --]
        [#--  --include "/WEB-INF/views/projects/messages-projects.ftl" / --]
      
        [@s.form action=actionName method="POST" enctype="multipart/form-data" cssClass=""]
          
          <h3 class="headTitle">[@s.text name="projectDescription.title" /]</h3>  
          <div id="projectDescription" class="borderBox">
            [#-- Project Title --]
            <div class="form-group">
              [@customForm.textArea name="project.name" i18nkey="projectDescription.name" required=true className="project-title" editable=editable && action.hasPermission("title") /]
            </div>
            <div class="form-group row">  
            [#-- Short name --]
              <div class="col-md-6">
                [@customForm.input name="project.shortName" i18nkey="projectDescription.shortName" type="text" disabled=!editable  required=true editable=editable /]
              </div>               
            </div>
            <div class="form-group row">  
              [#-- Start Date --]
              <div class="col-md-6">
                [@customForm.input name="project.startDate" i18nkey="projectDescription.startDate" type="text" disabled=!editable  required=true editable=editable /]
              </div> 
              [#-- End Date --]
              <div class="col-md-6">
                [@customForm.input name="project.endDate" i18nkey="projectDescription.endDate" type="text" disabled=!editable required=false editable=editable /]
              </div>
            </div>
            <div class="form-group row">
            [#-- Project contact --]
            <div class="col-md-12 partnerPerson-email userField  form-group">
              <input type="hidden" class="canEditEmail" value="" />
              [@customForm.input name="project.projectLeader.composedName" className='userName' type="text"  i18nkey="projectDescription.contactPerson" required=true readOnly=true editable=editable /]
              <input class="userId" type="hidden" name="project.projectLeader.id" value="${(project.projectLeader.id)!}" />   
              [#if editable]<div class="searchUser button-blue button-float">[@s.text name="form.buttons.searchUser" /]</div>[/#if]
            </div>
            <div class="clearfix"></div>
            [#-- Funding source --]
            <div class="form-group col-md-12">
              <div class="">
                <label>[@s.text name="projectDescription.fundingSource" /]</label>
                <div class="borderBox fundingSourceList" listname="project.fundingSources">
                  [#if project.fundingSources?has_content]
                    [#list project.fundingSources as fundingSource]
                      [@fundingSourceMacro element=fundingSource name="project.fundingSources"  index=fundingSource_index /]
                    [/#list]
                  [/#if]
                  <p class="text-center inf" style="display:${(project.fundingSources?has_content)?string('none','block')}">[@s.text name="projectDescription.notFundingSource" /]</p>
                </div>
                <div class="text-right">
                  <div class="button-green addFundingSource"><span class="glyphicon glyphicon-plus-sign"></span>[@s.text name="Add a funding source" /]</div>
                </div>
              </div>
            </div>
            <div class="clearfix"></div>
              [#-- Select the cross-cutting dimension(s) to this project? --]
              <div class="form-group col-md-12">
                <label for="">[@customForm.text name="projectDescription.crossCuttingDimensions" readText=!editable/] [@customForm.req required=editable/]</label>
                <div class="row">
                  <div class="col-md-12">
                    [#if editable]
                      <label class="checkbox-inline"><input type="checkbox" name="project.projectCrosscutingTheme.climateChange"   id="climate"   value="true" [#if (project.projectCrosscutingTheme.climateChange)!false ]checked="checked"[/#if] > Climate Change</label>
                      <label class="checkbox-inline"><input type="checkbox" name="project.projectCrosscutingTheme.genderYouth"    id="youth"    value="true" [#if (project.projectCrosscutingTheme.genderYouth)!false ]checked="checked"[/#if] > Gender and Youth</label>
                      <label class="checkbox-inline"><input type="checkbox" name="project.projectCrosscutingTheme.policiesInstitutions"    id="policies"    value="true" [#if (project.projectCrosscutingTheme.policiesInstitutions)!false ]checked="checked"[/#if] > Policies and Institutions</label>
                      <label class="checkbox-inline"><input type="checkbox" name="project.projectCrosscutingTheme.capacityDevelopment" id="capacity" value="true" [#if (project.projectCrosscutingTheme.capacityDevelopment)!false ]checked="checked"[/#if] > Capacity Development</label>
                      <label class="checkbox-inline"><input type="checkbox" name="project.projectCrosscutingTheme.bigData" id="bigData" value="true" [#if (project.projectCrosscutingTheme.bigData)!false ]checked="checked"[/#if] > Big Data</label>
                      <label class="checkbox-inline"><input type="checkbox" name="project.projectCrosscutingTheme.na"       id="na"       value="true" [#if (project.projectCrosscutingTheme.na)!false ]checked="checked"[/#if] > N/A</label>
                    [#else]
                      [#if (project.projectCrosscutingTheme.climateChange)!false ]<p class="checked"> Climate Change</p>[/#if]
                      [#if (project.projectCrosscutingTheme.genderYouth)!false ]<p class="checked"> Gender and Youth</p>[/#if]
                      [#if (project.projectCrosscutingTheme.policiesInstitutions)!false ]<p class="checked"> Policies and Institutions</p>[/#if]
                      [#if (project.projectCrosscutingTheme.capacityDevelopment)!false ]<p class="checked"> Capacity Development</p>[/#if]
                      [#if (project.projectCrosscutingTheme.bigData)!false ]<p class="checked"> Big Data</p>[/#if]
                      [#if (project.projectCrosscutingTheme.na)!false ]<p class="checked"> N/A</p>[/#if]
                    [/#if]
                  </div>
                </div>
                <br />
              </div>
          </div> 
           
          [#-- Section Buttons & hidden inputs--]
          <div class="fullPartBlock">      
            <div class="output panel tertiary" listname="project.outputs">
              <div class="panel-head"><label for="">[@customForm.text name="projectDescription.outputs" readText=!editable /]</label></div> 
              <div class="panel-body"> 
                <ul id="outputsBlock" class="list outputList">
                [#if  project.outputs?has_content]  
                  [#list project.outputs as output]
                     [@outputMacro element=output name="project.outputs" index=output_index isTemplate=false/]
                  [/#list] 
                [#else]
                  <p class="text-center outputInf"> [@s.text name="projectDescription.notOutputs" /] </p>  
                [/#if]  
                </ul>
                [#if editable]
                  [@customForm.select name="" label="" disabled=!canEdit i18nkey="" listName="outputs" keyFieldName="id"  displayFieldName="title" className="outputSelect" value="" /]
                [/#if] 
              </div>
            </div> 
          </div>          
          
      </div>
      [#-- Section Buttons & hidden inputs--]
          [#include "/WEB-INF/views/monitoring/project/buttons-projects.ftl" /]
             
          [/@s.form] 
    </div>  
</section>


[@fundingSourceMacro element={} name="project.fundingSources"  index=-1 isTemplate=true /]
[@outputMacro element={} name="project.outputs"  index=-1 isTemplate=true /]
[@usersForm.searchUsers/]
  
[#include "/WEB-INF/global/pages/footer.ftl"]


[#macro fundingSourceMacro element name index=-1 isTemplate=false]  
  [#assign fundingSourceCustomName = "${name}[${index}]" /]
  <div id="fundingSource-${isTemplate?string('template',(element.id)!)}" class="fundingSources  borderBox row"  style="display:${isTemplate?string('none','block')}">
    [#if editable]<div class="removeFundingSource removeIcon" title="Remove funding source"></div>[/#if] 
    <input class="id" type="hidden" name="${fundingSourceCustomName}.id" value="${(element.id)!-1}" />
    <div class="col-md-4">
      [@customForm.select name="${fundingSourceCustomName}.fundingSourceType.id" label=""  i18nkey="Funding source" listName="fundingSourceTypes" keyFieldName="id"  displayFieldName="name"  multiple=false required=true header=false className="" editable=editable/]
    </div>
    <div class="col-md-8">
      [@customForm.input name="${fundingSourceCustomName}.donor" i18nkey="Donor" type="text" disabled=!editable  required=false editable=editable /]
    </div>
    <div class="clearfix"></div>
  </div>
[/#macro]

[#macro outputMacro element name index=-1 isTemplate=false]  
  [#assign outputCustomName = "${name}[${index}]" /]
  <li id="output-${isTemplate?string('template',(element.id)!)}" class="outputs  borderBox expandableBlock row "  style="display:${isTemplate?string('none','block')}">
  <input type="hidden" class="outputId" name="${outputCustomName}.researchOutput.id" value="${(element.researchOutput.id)!}"/>
    [#if editable] [#--&& (isTemplate) --]
      <div class="removeLink">
        <div id="" class="removeOutput removeElement removeLink" title="[@s.text name='projectDecription.removeOutput' /]"></div>
      </div>
    [/#if]
    <div class="leftHead">
        <span class="index">O${(element.researchOutput.id)!}</span>
      </div>
    [#-- output Title --]
    <div class="blockTitle closed form-group" style="margin-top:30px;">
      <label for="">Output statement</label>
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