<template>
  <div class="row">
    <div class="col-1"></div>
    <div class="col-10">
      <div class="row">
        <div class="container rounded">
          <p>GRAPH</p>
        </div>
      </div>
      <div class="row">
        <div class="col-3">
          <income-rate-view></income-rate-view>
        </div>
        <div class="col-9">
          <div class="container rounded">
            <p>USER CATEGORIES</p>
          </div>
        </div>
      </div>
    </div>
    <div class="col-1"></div>
  </div>
</template>

<script>

import IncomeRateView
  from "@/components/Administrator/AdminPage/components/AdminMainViews/AdminIncomeIncomeRateComponents/IncomeRateView";
export default {
  name: "AdminContentPage",
  components: {IncomeRateView},
}

</script>

<style scoped>
.container {
  outline: solid 2px #3f5b25;
  margin-top: 10px;
  color: #3f5b25;
}
</style>