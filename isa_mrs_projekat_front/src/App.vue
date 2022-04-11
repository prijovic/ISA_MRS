<template>
  <the-navbar></the-navbar>
  <router-view name="sidebar"/>
  <div class="container">
    <router-view :style="{'margin-left': sidebarWidth, 'padding': sidebarWidth}"/>
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
  components: {
    TheNavbar
  }
}

</script>

<style>
@import "~bootstrap/dist/css/bootstrap.css";
</style>
