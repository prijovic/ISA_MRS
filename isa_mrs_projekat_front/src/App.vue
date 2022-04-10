<template>
  <router-view/>
  <the-navbar></the-navbar>
  <router-view name="sidebar"/>
  <div class="container p-0">
    <router-view :style="{'margin-left': sidebarWidth, 'padding': sidebarWidth}"/>
  </div>
</template>

<script>
export default {
  name: 'App'
}

</script>

<style>
@import "~bootstrap/dist/css/bootstrap.css";
</style>
