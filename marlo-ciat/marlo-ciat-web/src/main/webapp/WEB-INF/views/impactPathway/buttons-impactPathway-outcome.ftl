[#ftl]
<div class="buttons">
  <div class="buttons-content">
    [#-- History Log --]
    [#if action.getListLog(outcome)?has_content]
      [#import "/WEB-INF/global/macros/logHistory.ftl" as logHistory /]
      [@logHistory.logList list=action.getListLog(outcome) itemName="outcomeID" itemId=outcomeID /]
      <a href="" onclick="return false" class="form-button button-history"><span class="glyphicon glyphicon-glyphicon glyphicon-list-alt" aria-hidden="true"></span> [@s.text name="form.buttons.history" /]</a>
    [/#if]
    [#if editable]
      [#-- Save Button --]
      [@s.submit type="button" name="save" cssClass="button-save"]<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span> [@s.text name="form.buttons.save" /][/@s.submit]
    [#elseif canEdit]
      [#-- Edit Button --]
      <a href="[@s.url][@s.param name="programID" value=programID /][@s.param name="outcomeID" value=outcomeID /][@s.param name="edit" value="true"/][/@s.url]" class="form-button button-edit"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span> [@s.text name="form.buttons.edit" /]</a>
    [/#if]
    [#-- Hidden Parameters --]
    <input type="hidden" name="programID" value="${(selectedProgram.id)!}" />
    <input type="hidden"  name="className" value="${(selectedProgram.class.name)!}"/>
    <input type="hidden"  name="id" value="${(selectedProgram.id)!}"/>
    <input type="hidden"  name="modifiedBy.id" value="${(currentUser.id)!}"/>
    <input type="hidden"  name="actionName" value="${(actionName)!}"/>  
    <input type="hidden" name="outcomeID" value="${outcomeID}" />
  </div>
</div>