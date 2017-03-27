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
      [#-- Outcomes List --]
      <h3 class="headTitle text-center"></h3>
      <div class="simpleBox col-md-12">
        <div class="col-md-4">
          <label for="">Research topic:  </label>
          <p>${selectedResearchTopic.researchTopic}</p>
        </div>
        <div class="col-md-4">
          <label for="">Outcome name:  </label>
          <p>test${(outcome.name)!}</p>
        </div>
        <div class="col-md-4">
          <label for="">Expected for 2022:  </label>
          <p>3${(outcome.name)!}</p>
        </div>
      </div>
    <div class="clearfix"></div>
    <div class="">
      [#-- Year Tabs --]
      <ul class="nav nav-tabs" role="tablist">
        [#list outcome.monitorings as year]
          <li class="[#if year.year == action.getYear()]active[/#if]"><a href="#fundingYear-${year.year}" role="tab" data-toggle="tab">${year.year} </a></li>
        [/#list]
      </ul>
      [#-- Years Content --]
      <div class="tab-content col-md-12" style="border: 1px solid #e7e7e7;">
        [#list outcome.monitorings as year]
        <br />
          <div role="tabpanel" class="tab-pane [#if year.year == action.getYear()]active[/#if]" id="fundingYear-${year}">
          ${year_index}
          [#if year_index==0]
          <div class="col-md-2">
            [@customForm.input name="" i18nkey="Initial Baseline" required=true editable=true /]
          </div>
          <div class="clearfix"></div>
          [/#if]
          <h5 class="sectionSubTitle">Milestones/ progress towards your outcome target contribution:</h5>
          <div class="simpleBox">
          </div>
          
          </div>
        [/#list] 
      </div>
    </div>
      
         
      <div class="clearfix"></div>
    </div>
    
  </article>
</section>


[#include "/WEB-INF/global/pages/footer.ftl"]
