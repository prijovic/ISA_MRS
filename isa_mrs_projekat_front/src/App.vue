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
  outline: solid 2px #008970;
  margin-top: 10px;
  color: #008970;
  background-color: rgb(247, 247, 242);
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

.sidebar {
  color: #99EEDF;
  background-color: #008970;
  float: left;
  position: fixed;
  z-index: 100;
  top: 77px;
  left: 0;
  bottom: 0;
  padding: 0.5em;
  transition: 0.3s ease;
  display: flex;
  flex-direction: column;
}

.collapse-icon {
  position: absolute;
  bottom: 10px;
  left: 10px;
  color: #f7f7f2;
  transition: 0.2s linear;
}

.rotate-180 {
  transform: rotate(180deg);
  transition: 0.2s linear;
}
</style>
