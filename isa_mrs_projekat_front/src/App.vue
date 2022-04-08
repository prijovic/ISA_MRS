<template>
  <router-view name="navbar"/>
  <router-view name="sidebar"/>
  <div class="container">
    <router-view :style="{'margin-left': sidebarWidth, 'padding': sidebarWidth}"/>
  </div>
</template>

<script>
import {sidebarWidth} from "@/components/state";

export default {
  name: 'App',
  setup() {
    return {sidebarWidth};
  }
}

</script>

<style>
@import "~bootstrap/dist/css/bootstrap.css";
</style>
