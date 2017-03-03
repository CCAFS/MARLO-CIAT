[#ftl]
[#assign title = "Project Description" /]
[#assign currentSectionString = "project-${actionName?replace('/','-')}-${projectID}" /]
[#assign pageLibs = ["select2"] /]
[#assign customJS = ["${baseUrl}/js/projects/projectDescription.js", "",""] /]
[#assign currentSection = "projects" /]
[#assign currentStage = "description" /]
[#assign editable = true /]

[#assign breadCrumb = [
  {"label":"projectsList", "nameSpace":"/monitoring", "action":"${(centerSession)!}/projectList"},
  {"label":"projectDescription", "nameSpace":"/monitoring", "action":""}
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
      
        [@s.form action=actionName method="POST" enctype="multipart/form-data" cssClass=""]
          
          <h3 class="headTitle">[@s.text name="projectDescription.title" /]</h3>  
          <div id="projectDescription" class="borderBox">
            [#-- Project Title --]
            <div class="form-group">
              [@customForm.textArea name="project.name" i18nkey="Title" required=true className="project-title" editable=editable && action.hasPermission("title") /]
            </div>
            <div class="form-group row">  
            [#-- Short name --]
              <div class="col-md-6">
                [@customForm.input name="project.shortName" i18nkey="CIAT short name" type="text" disabled=!editable  required=true editable=editable /]
              </div> 
              [#-- End Date --]
              <div class="col-md-6">
                [@customForm.input name="project.pl" i18nkey="Pl" type="text" disabled=!editable required=true editable=editable /]
              </div>
            </div>
            <div class="form-group row">  
              [#-- Start Date --]
              <div class="col-md-6">
                [@customForm.input name="project.startDate" i18nkey="Start date" type="text" disabled=!editable  required=true editable=editable /]
              </div> 
              [#-- End Date --]
              <div class="col-md-6">
                [@customForm.input name="project.endDate" i18nkey="End date" type="text" disabled=!editable required=false editable=editable /]
              </div>
            </div>
            <div class="form-group row">
              [#-- Project contact --]
              <div class="col-md-12 form-group">
                [@customForm.input name="project.contactPerons" i18nkey="Project contact(Project leader)" type="text" disabled=!editable  required=true editable=editable /]
              </div> 
            <div class="clearfix"></div>
            [#-- Funding source --]
            <div class="form-group col-md-12">
              <div class="">
                <label>Funding Source(s)</label>
                <div class="borderBox fundingSourceList" listname="project.fundingSources">
                  [#if project?has_content]
                    [#list project.fundingSources as fundingSource]
                        [@fundingSourceMacro element=fundingSource name="project.fundingSources"  index=fundingSource_index /]
                    [/#list]
                  [/#if]
                  <p class="text-center inf" style="display:${(project?has_content)?string('none','block')}">[@s.text name="projectDescription.notFundingSource" /]</p>
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
                      <label class="checkbox-inline"><input type="checkbox" name="project.crossCuttingGender"   id="gender"   value="true" > Gender</label>
                      <label class="checkbox-inline"><input type="checkbox" name="project.crossCuttingYouth"    id="youth"    value="true" > Youth</label>
                      <label class="checkbox-inline"><input type="checkbox" name="project.crossCuttingCapacity" id="capacity" value="true" > Capacity Development</label>
                      <label class="checkbox-inline"><input type="checkbox" name="project.crossCuttingNa"       id="na"       value="true" > N/A</label>
                    [#else]
                       <p class="checked"> Gender</p>
                       <p class="checked"> Youth</p>
                      <p class="checked"> Capacity Development</p>
                      <p class="checked"> N/A</p>
                    [/#if]
                  </div>
                </div>
                <br />
              </div>
          </div> 
           
          [#-- Section Buttons & hidden inputs--]
          [#--  --include "/WEB-INF/views/projects/buttons-projects.ftl" / --]
             
         
          [/@s.form] 
      </div>
    </div>  
</section>


[@fundingSourceMacro element={} name="project.fundingSources"  index=-1 isTemplate=true /]
  
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