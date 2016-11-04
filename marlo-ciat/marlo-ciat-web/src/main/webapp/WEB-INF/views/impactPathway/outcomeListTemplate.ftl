[#ftl]
[#import "/WEB-INF/global/macros/utils.ftl" as utilities/]

[#macro outcomesList outcomes={} owned=true canValidate=false canEdit=false isPlanning=false namespace="/" defaultAction=""]
  <table class="outcomeList" id="outcomes">
    <thead>
      <tr class="subHeader">
        <th id="ids">[@s.text name="programImpact.outcomeList.idTable" /]</th>
        <th id="outcomeTitles" >[@s.text name="programImpact.outcomeList.statement" /]</th>
        <th id="outcomeTargetYear">[@s.text name="programImpact.outcomeList.targetYear" /]</th>
      </tr>
    </thead>
    <tbody>
    [#if outcomes?has_content]
      [#list outcomes as outcome]
        <tr>
        [#-- ID --]
        <td class="deliverableId">
          <a href="[@s.url namespace=namespace action=defaultAction][@s.param name='deliverableID']${outcome.id?c}[/@s.param][@s.param name='edit']true[/@s.param][/@s.url]">OC${outcome.id}</a>
        </td>
          [#-- outcome statement --]
          <td class="left"> 
            [#if outcome.description?has_content]
                <a href="[@s.url namespace=namespace action=defaultAction] [@s.param name='outcomeID']${outcome.id?c}[/@s.param][@s.param name='edit']true[/@s.param][/@s.url]" title="${outcome.description}">
                [#if outcome.description?length < 120] ${outcome.description}</a> [#else] [@utilities.wordCutter string=outcome.description maxPos=120 /]...</a> [/#if]
            [#else]
                <a href="[@s.url namespace=namespace action=defaultAction includeParams='get'] [@s.param name='outcomeID']${outcome.id?c}[/@s.param][@s.param name='edit']true[/@s.param][/@s.url] ">
                  [@s.text name="projectsList.title.none" /]
                </a>
            [/#if]
          </td>
          [#-- outcome Year --]
          <td class="text-center">
          [#if outcome.year== -1]
          none
          [#else]
          ${(outcome.year)!'none'}
          [/#if]
            
          </td>
          
        </tr>  
      [/#list]
      [/#if]
    </tbody>
  </table>
[/#macro]