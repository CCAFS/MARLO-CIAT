[#ftl]
[#assign title = "Outcomes List" /]
[#assign currentSectionString = "program-${actionName?replace('/','-')}-${programID}" /]
[#assign pageLibs = ["datatables.net", "datatables.net-bs","select2"] /]
[#assign customJS = ["${baseUrl}/js/global/usersManagement.js", "${baseUrl}/js/impactPathway/outcomeList.js", "${baseUrl}/js/global/fieldsValidation.js"] /]
[#assign customCSS = ["${baseUrl}/css/global/customDataTable.css","${baseUrl}/css/impactPathway/outcomeList.css"] /]
[#assign currentSection = "impactPathway" /]
[#assign currentStage = "outcomes" /]

[#assign breadCrumb = [
  {"label":"impactPathway", "nameSpace":"", "action":"topics"}
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
         

        <span id="programSelected" class="hidden">${(selectedProgram.id)!}</span>
        
        [#-- Back --]
        <small class="pull-right">
          <a href="[@s.url action='${centerSession}/outcomesList'][@s.param name="programID" value=programID /][/@s.url]">
            <span class="glyphicon glyphicon-circle-arrow-left"></span> Back to the outcomes list
          </a>
        </small>
        
        <div class="clearfix"></div>
        ${outcome}
        
        [@s.form action=actionName enctype="multipart/form-data" ]
        
        <div class="borderBox">
          <h5 class="sectionSubTitle"> Outcome Information</h5>
          [#assign outcomeCustomName= "outcome" /]
          [#-- Outcome Statement --]
          <div class="form-group">
            [@customForm.textArea name="${outcomeCustomName}.description"  i18nkey="outcome.statement" required=true className="outcome-statement limitWords-100" editable=editable /]
          </div>
          <div class="row form-group target-block">
            [#-- Target Year --]
            <div class="col-md-4">[@customForm.input name="${outcomeCustomName}.year" value="${(outcome.year)!2022}" type="text" i18nkey="outcome.targetYear"  placeholder="outcome.inputTargetYear.placeholder" className="targetYear outcomeYear" required=true editable=editable /]</div>
            [#-- Target Unit --]
            <div class="col-md-4">
              [@customForm.select name="${outcomeCustomName}.srfTargetUnit.id" i18nkey="outcome.selectTargetUnit"  placeholder="outcome.selectTargetUnit.placeholder" className="targetUnit" listName="targetUnitList" editable=editable  /]
              [#-- If you dont find the target unit in the list, please add a new one clicking here --]
              [#--  --if editable]<div class="addOtherTargetUnit text-center"><a href="#">([@s.text name = "outcomes.addNewTargetUnit" /])</a></div>[/#if --]
            </div>
            [#-- Target Value --]
            
            [#assign showTargetValue = (outcome.srfTargetUnit??) && (outcome.srfTargetUnit.id??) && (outcome.srfTargetUnit.id != -1) /]
            <div class="col-md-4 targetValue-block" style="display:${showTargetValue?string('block', 'none')}">
              [@customForm.input name="${outcomeCustomName}.value" type="text" i18nkey="outcome.targetValue" placeholder="outcome.inputTargetValue.placeholder" className="targetValue" required=true editable=editable /]
            </div>
            
      
          </div>  
          <div class="col-md-12 note">[@s.text name = "outcomes.addNewTargetUnit" /]</div>
          
        </div>
          
          
          
        [/@s.form]
        
      </div>
    </div>
    
  </div>
</section>

[#include "/WEB-INF/global/pages/footer.ftl" /]


