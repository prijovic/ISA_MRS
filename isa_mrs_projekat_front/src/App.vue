<template>
  <the-navbar></the-navbar>
  <router-view name="sidebar"/>

  <div class="container-fluid" :style="{'padding-left': sidebarWidth}">
    <router-view :key="$route.path"/>
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

.btn-red {
  background-color: #e23c52;
  color: white;
  border: 1px solid rgb(226, 60, 82, 0.4);
}

.btn-red:active, .btn-red:hover, .btn-red:focus {
  background-color: rgb(226, 60, 82, 0.4);
  color: white;
  border: 1px solid #e23c52;
}

input[type=checkbox] {
  opacity: 100%;
  border: 1px solid #008970;
  cursor: pointer;
}

input[type=checkbox]:checked {
  background-color: rgb(0, 137, 112);
  border: 1px solid #008970;
}

.odd {
  background-color: rgba(176, 184, 180, 0.3);
}

.even {
  background-color: white;
}

thead {
  background-color: #008970;;
  color: #99EEDF;
  /*border-bottom: 1px solid #008970;*/
  /*border-top: 1px solid #008970;*/
}

.eye-btn {
  color: #008970;
  background-color: rgb(153, 238, 223, 0)
}

.eye-btn:active, .eye-btn:hover, .eye-btn:focus {
  color: rgb(0, 137, 112, 0.4);
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

.page-link {
  color: #008970;
}

.page-link:hover, .page-link:active, .page-link:focus {
  color: #008970;
}

.page-item {
  color: #008970;
}

.button:disabled, button[disabled] {
  background-color: #b0b8b4ff;
  color: #99EEDF;
}

.button:disabled:hover, button[disabled]:hover, .button:disabled:active, button[disabled]:active, .button:disabled:focus, button[disabled]:focus {
  background-color: #b0b8b4ff;
  color: #99EEDF;
}
</style>
