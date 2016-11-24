[#ftl]
[#-- Output --]
<div class="sectionSubMenu">
  [#-- Nav tabs --]
  <ul class="nav nav-tabs" role="tablist">
      <li role="areas" class="active"> 
        <a href="[@s.url action="${centerSession}/outputs"][@s.param name="outputID" value=outputID /][/@s.url]" aria-controls="home" role="tab" [#-- data-toggle="tab" --]>Main Information</a>
      </li>
      <li role="areas" class=""> 
        <a href="[@s.url action="${centerSession}/outputsPartners"][@s.param name="outputID" value=outputID /][/@s.url]" aria-controls="home" role="tab" [#-- data-toggle="tab" --]>Partners</a>
      </li>
  </ul>
  
</div>