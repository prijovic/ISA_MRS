<template>
  <the-navbar></the-navbar>
  <router-view name="sidebar"/>

  <div class="container-fluid" :style="{'padding-left': sidebarWidth}">
    <router-view/>
    <notifications position="bottom right" :max=maxNotif style="font-size: 200px"/>
  </div>
</template>

<script>
import {sidebarWidth} from "@/components/state";
import TheNavbar from "@/components/GeneralComponents/TheNavbar";

export default {
  name: 'App',
  setup() {
    return {sidebarWidth};
  },
  computed: {
    maxNotif() {
      return 1;
    }
  },
  components: {
    TheNavbar
  }
}

</script>

<style>
@import "~bootstrap/dist/css/bootstrap.css";
.form {
  outline: solid 2px #3f5b25;
  margin-top: 10px;
  color: #3f5b25;
  background-color: #f7f7f2;
}

.btn {
  background-color: #008970;
  color: #99EEDF;
  border: 1px solid #99EEDF;
}

.btn:active, .btn:hover, .btn:focus {
  background-color: rgb(0, 137, 112, 0.4);
  color: white;
  border: 1px solid #008970;
}

input[type=checkbox] {
  opacity: 100%;
  border: none;
}

input[type=checkbox]:checked {
  background-color: rgb(0, 137, 112);
}
</style>
