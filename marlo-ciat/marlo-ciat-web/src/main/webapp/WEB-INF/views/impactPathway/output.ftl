[#ftl]
[#assign title = "Outcome" /]
[#assign currentSectionString = "program-${actionName?replace('/','-')}-${programID}" /]
[#assign pageLibs = ["datatables.net", "datatables.net-bs","select2"] /]
[#assign customJS = ["${baseUrl}/js/global/usersManagement.js", "${baseUrl}/js/impactPathway/output.js", "${baseUrl}/js/global/fieldsValidation.js"] /]
[#assign customCSS = ["${baseUrl}/css/global/customDataTable.css","${baseUrl}/css/impactPathway/outputList.css"] /]
[#assign currentSection = "impactPathway" /]
[#assign currentStage = "outputs" /]

[#assign breadCrumb = [
  {"label":"impactPathway", "nameSpace":"impactPathway", "action":"programImpacts"},
  {"label":"outputsList", "nameSpace":"${centerSession}/impactPathway", "action":"outputsList"},
  {"label":"output", "nameSpace":"", "action":""}
]/]
[#assign leadersName = "leaders"/]
[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/utils.ftl" as utils /]
[#import "/WEB-INF/views/impactPathway/outputListTemplate.ftl" as outcomesList /]
[#--  marlo cluster of activities--]
<section class="marlo-content">
  <div class="container"> 
    
    <div class="row">
      <div class="col-md-3">
        [#include "/WEB-INF/views/impactPathway/menu-impactPathway.ftl" /]
      </div>
      <div class="col-md-9">
        [#-- Section Messages --]
        [#include "/WEB-INF/views/impactPathway/messages-impactPathway-output.ftl" /]
        [#-- Impact pathway sub menu --]
        [#include "/WEB-INF/views/impactPathway/submenu-impactPathway-output.ftl" /]

        <span id="programSelected" class="hidden">${(selectedProgram.id)!}</span>
        [#-- Impact pathway sub menu --]
        <div class="simpleBox col-md-12">
            <label for="">Research Topic:  </label>
            <p>${selectedResearchTopic.researchTopic}</p>
        </div>
        
        <div class="simpleBox col-md-12">
            <label for="">Outcome:  </label>
            <p>${selectedResearchOutcome.description}</p>
        </div>
        
        <br />
        <br />
        
        [#-- Back --]
        <small class="pull-right">
          <a href="[@s.url action='${centerSession}/outputsList'][@s.param name="programID" value=programID /][@s.param name="outcomeID" value=outcomeID /][@s.param name="edit" value=true /][/@s.url]">
            <span class="glyphicon glyphicon-circle-arrow-left"></span> Back to the outputs list
          </a>
        </small>
        
        <div class="clearfix"></div>
        
        [@s.form action=actionName enctype="multipart/form-data" ]
        
        <div class="borderBox">
          <h5 class="sectionSubTitle"> Output description</h5>
          [#assign outputCustomName= "output" /]
          
          [#-- Contact Person --]
          [#if contacPersons?has_content]
            <label for="">Contact Person:  </label>
            [#list contacPersons as contacPerson]
            <p>${contacPerson.user.composedCompleteName}</p>
            [/#list]
          [/#if] 
          
          [#-- Output Name --]
          <div class="form-group">
            [@customForm.textArea name="${outputCustomName}.title"  i18nkey="output.name" required=true className="output-name limitWords-100" editable=editable /]
          </div> 
        </div>
          
        [#-- Section Buttons--]
        [#include "/WEB-INF/views/impactPathway/buttons-impactPathway-output.ftl" /]
        
        [#-- Hidden inputs --]
        <input type="hidden" name="outputID" value="${outputID}" />
          
        [/@s.form]
        
      </div>
    </div>
    
  </div>
</section>

[#include "/WEB-INF/global/pages/footer.ftl" /]
