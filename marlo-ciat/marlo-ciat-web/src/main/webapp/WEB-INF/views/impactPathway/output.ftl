[#ftl]
[#assign title = "Outcome" /]
[#assign currentSectionString = "program-${actionName?replace('/','-')}-${programID}" /]
[#assign pageLibs = ["select2"] /]
[#assign customJS = ["${baseUrl}/js/impactPathway/output.js", "${baseUrl}/js/global/fieldsValidation.js"] /]
[#assign customCSS = ["${baseUrl}/css/global/customDataTable.css","${baseUrl}/css/impactPathway/outputList.css"] /]
[#assign currentSection = "impactPathway" /]
[#assign currentStage = "outputs" /]
[#assign currentSubStage = "mainInformation" /]

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

[#assign outputCustomName= "output" /]

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
        

        <span id="programSelected" class="hidden">${(selectedProgram.id)!}</span>
        
        [#-- Back --]
        <small class="pull-right">
          <a href="[@s.url action='${centerSession}/outputsList'][@s.param name="programID" value=programID /][@s.param name="outcomeID" value=outcomeID /][@s.param name="edit" value=true /][/@s.url]">
            <span class="glyphicon glyphicon-circle-arrow-left"></span> Back to the outputs list
          </a>
        </small>
        
        <div class="clearfix"></div>
        
        [#-- Impact pathway sub menu --]
        [#include "/WEB-INF/views/impactPathway/submenu-impactPathway-output.ftl" /]
        
        [@s.form action=actionName enctype="multipart/form-data" ]
        <h3 class="headTitle"> Output description </h3>
        <div class="borderBox">
          <!--h5 class="sectionSubTitle"> Output description</h5-->
          
          [#-- Output Name --]
          <div class="form-group">
            [@customForm.textArea name="${outputCustomName}.title"  i18nkey="output.name" required=true className="output-name limitWords-100" editable=editable /]
          </div> 
          
          [#-- Research topic --]
          <div class="form-group">
              <label for="">Research Topic:</label><p>${selectedResearchTopic.researchTopic}</p>
          </div>
          
          [#-- Outcome --]
          <div class="form-group">
              <label for="">Outcome:</label><p>${selectedResearchOutcome.description}</p>
          </div>
          
          [#-- Contact Person --]
          [#if contacPersons?has_content]
            <label for="">Contact Person(s):  </label>
            [#list contacPersons as contacPerson]
            <p> <span class="glyphicon glyphicon-user"></span> ${contacPerson.user.composedCompleteName}</p>
            [/#list]
          [/#if] 
        </div>
        
        <h3 class="headTitle"> Next Users </h3>
        <div class="borderBox">
          Comming...
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