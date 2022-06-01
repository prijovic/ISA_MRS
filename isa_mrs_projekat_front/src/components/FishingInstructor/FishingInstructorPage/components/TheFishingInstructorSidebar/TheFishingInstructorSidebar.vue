<template>
  <div class="sidebar rounded-right" :style="{width:sidebarWidth}">
    <MenuLink to="/#" icon="home">Home</MenuLink>
    <MenuLink to="/fishingInstructor/adventures" icon="mountain-sun">Adventures</MenuLink>
    <MenuLink to="/#" icon="book">Reservation History</MenuLink>
    <MenuLink to="/#" icon="calendar">Calendar</MenuLink>
    <span class="collapse-icon" :class="{'rotate-180': collapsed}" @click="toggleSidebar">
      <font-awesome-icon icon="angle-double-left"></font-awesome-icon>
    </span>
  </div>
</template>

<script>
import {collapsed, toggleSidebar, sidebarWidth} from "@/components/state";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faAngleDoubleLeft, faMountainSun, faHome, faBook, faCalendar} from "@fortawesome/free-solid-svg-icons";
import MenuLink from "@/components/GeneralComponents/MenuLink";

library.add(faAngleDoubleLeft, faMountainSun, faHome, faBook, faCalendar);

export default {
  name: "TheFishingInstructorSidebar",
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

</style>