[#ftl]
[#assign title = "Management" /]
[#assign currentSectionString = "${actionName?replace('/','-')}" /]
[#assign pageLibs = ["vanilla-color-picker"] /]
[#assign customJS = ["${baseUrl}/js/global/usersManagement.js", "${baseUrl}/js/admin/management.js" ] /]
[#assign currentSection = "admin" /]
[#assign currentStage = "management" /]

[#assign breadCrumb = [
  {"label":"menu.superadmin.admin", "nameSpace":"/admin", "action":"coordination"},
  {"label":"management", "nameSpace":"", "action":""}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]

<section class="marlo-content">
  <div class="container"> 
    <div class="row">
      <div class="col-md-3">
        [#include "/WEB-INF/views/admin/menu-admin.ftl" /]
      </div>
      <div class="col-md-9">
        [@s.form action=actionName enctype="multipart/form-data" ]  
        
        <h4 class="sectionTitle">[@s.text name="programManagement.title" /]</h4>
        
        [/@s.form]
      </div>
    </div>
  </div>
</section>

[#-- Search users Interface --]
[#import "/WEB-INF/global/macros/usersPopup.ftl" as usersForm/]
[@usersForm.searchUsers/]

[#-- Program template --]
[@programItem element={} index=0 name="" template=true /]

<ul style="display:none">
  [#-- User template --]
  [@userItem element={} index=0 name="" userRole="-1" template=true /]
</ul>

[#include "/WEB-INF/global/pages/footer.ftl" /]
