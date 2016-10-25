[#ftl]
<div class="buttons">
  <div class="buttons-content">
    [#if editable]
      [#-- Save Button --]
      [@s.submit type="button" name="save" cssClass="button-save"]<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span> [@s.text name="form.buttons.save" /][/@s.submit]
    [/#if]
    <input type="hidden" name="programID" value="${programID}" />
  </div>
</div>