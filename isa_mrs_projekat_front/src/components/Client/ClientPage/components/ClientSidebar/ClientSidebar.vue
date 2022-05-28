<template>
  <div class="sidebar rounded-right" :style="{width:sidebarWidth}">
    <MenuLink to="/client/profile" icon="id-card">Profile</MenuLink>
    <hr>
    <MenuLink to="/client/adventures" icon="mountain-sun">Adventures</MenuLink>
    <MenuLink to="/client/boats" icon="ship">Boats</MenuLink>
    <MenuLink to="/client/vacationRentals" icon="house">Rental Houses</MenuLink>
    <span class="collapse-icon" :class="{'rotate-180': collapsed}" @click="toggleSidebar">
      <font-awesome-icon icon="angle-double-left"></font-awesome-icon>
    </span>
  </div>
</template>

<script>
import {collapsed, toggleSidebar, sidebarWidth} from "@/components/state";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faAngleDoubleLeft, faHouse, faShip, faMountainSun, faIdCard} from "@fortawesome/free-solid-svg-icons";
import MenuLink from "@/components/GeneralComponents/MenuLink";
library.add(faAngleDoubleLeft, faHouse, faShip, faMountainSun, faIdCard);
export default {
  name: "ClientSidebar",
  components: {
    MenuLink,
    FontAwesomeIcon,
  },
  setup() {
    return {collapsed, toggleSidebar, sidebarWidth};
  }
}
</script>

<style scoped>
.sidebar {
  color: #f7f7f2;
  background-color: #378220;
  float: left;
  position: fixed;
  z-index: 0;
  top: 73px;
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