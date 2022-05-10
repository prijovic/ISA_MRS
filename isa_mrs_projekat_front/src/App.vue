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
</style>
