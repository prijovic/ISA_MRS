<template>
    <div class="lineTitle">
        <p class="h4">Rules</p>
        <hr class="ms-1">
    </div>
    <div class="doOrDont d-flex justify-content-center">
        <div class="col-md-4 pe-3">
            <div class="center-block text-center">
                <p class="h5">Do's</p>
            </div>
            <p v-for="(r, i) in dos" :key="i" class="h6"><i class="bi bi-check-circle-fill me-2" style="color:darkblue"></i>
                {{r.rule}}</p>
        </div>
        <div class="col-md-4 ps-3">
            <div class="center-block text-center">
                <p class="h5">Don'ts</p>
            </div>
            <p v-for="(r, i) in donts" :key="i" class="h6"><i class="bi bi-x-circle-fill me-2" style="color:darkblue"></i>
                {{r.rule}}</p>
        </div>
    </div>
</template>

<script>
export default {
  name: "RentalRules",
  props: ["conductRules"],
  computed: {
    dos(){
      let doRules = [];
      this.conductRules.forEach(conductRule => conductRule.type === "Do" ? doRules.push(conductRule) : null);
      return doRules;
    },
    donts(){
      let dontRules = [];
      this.conductRules.forEach(conductRule => conductRule.type === "DoNot" ? dontRules.push(conductRule) : null);
      return dontRules;
    }
  }
}
</script>

<style scoped>
  div.doOrDont {
    text-align: justify;
  }

  div.lineTitle {
    display: flex;
  }
</style>