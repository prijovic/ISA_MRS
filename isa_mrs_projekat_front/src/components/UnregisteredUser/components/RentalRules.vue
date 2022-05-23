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
            <p v-for="(r, i) in dos" :key="i" class="h6"><font-awesome-icon class="me-1" icon="circle-check" style="color:darkblue"></font-awesome-icon>
                {{r.rule}}</p>
        </div>
        <div class="col-md-4 ps-3">
            <div class="center-block text-center">
                <p class="h5">Don'ts</p>
            </div>
            <p v-for="(r, i) in donts" :key="i" class="h6"><font-awesome-icon class="me-1" icon="circle-xmark" style="color:darkblue"></font-awesome-icon>
                {{r.rule}}</p>
        </div>
    </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faCircleCheck } from "@fortawesome/free-solid-svg-icons";
import { faCircleXmark } from "@fortawesome/free-solid-svg-icons";

library.add(faCircleCheck);
library.add(faCircleXmark);

export default {
  name: "RentalRules",
  components: {FontAwesomeIcon},
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