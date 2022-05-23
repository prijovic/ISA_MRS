<template>
  <div class="sidebar rounded-right" :style="{width:sidebarWidth}">
    <MenuLink to="/" icon="house-laptop">Home</MenuLink>
    <MenuLink to="/registration" icon="id-card-clip">Registration</MenuLink>
    <hr>
    <MenuLink to="/#" icon="mountain-sun">Adventures</MenuLink>
    <MenuLink to="/#" icon="ship">Boats</MenuLink>
    <MenuLink to="/#" icon="house">Rental Houses</MenuLink>
    <span class="collapse-icon" :class="{'rotate-180': collapsed}" @click="toggleSidebar">
      <font-awesome-icon icon="angle-double-left"></font-awesome-icon>
    </span>
  </div>
</template>

<script>
import MenuLink from "@/components/GeneralComponents/MenuLink";
import {collapsed, toggleSidebar, sidebarWidth} from "@/components/state";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faAngleDoubleLeft, faHouse, faShip, faMountainSun, faIdCardClip, faHouseLaptop } from "@fortawesome/free-solid-svg-icons";

library.add(faAngleDoubleLeft, faHouse, faShip, faMountainSun, faIdCardClip, faHouseLaptop)

export default {
  name: "TheUnregisteredUserNavbar",
  components: {
    MenuLink,
    FontAwesomeIcon
  },
  setup() {
    return {collapsed, toggleSidebar, sidebarWidth};
  }
}
</script>

<style scoped>

</style>