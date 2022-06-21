<template>
  <div class="sidebar rounded-right" :style="{width:sidebarWidth}">
    <MenuLink to="/vacationRentalOwner" icon="home">Home</MenuLink>
    <MenuLink to="/vacationRentalOwner/cottages" icon="house-chimney-window">Rental Houses</MenuLink>
    <MenuLink to="/vacationRentalOwner/calendar" icon="calendar-days">Calendar</MenuLink>
    <MenuLink to="/vacationRentalOwner/finances" icon="coins">Finances</MenuLink>
    <span class="collapse-icon" :class="{'rotate-180': collapsed}" @click="toggleSidebar">
      <font-awesome-icon icon="angle-double-left"></font-awesome-icon>
    </span>
  </div>
</template>

<script>
import {collapsed, toggleSidebar, sidebarWidth} from "@/components/state";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faAngleDoubleLeft, faHome, faHouseChimneyWindow, faCalendarDays, faComments, faIdCard, faCoins} from "@fortawesome/free-solid-svg-icons";
import MenuLink from "@/components/GeneralComponents/MenuLink";

library.add(faAngleDoubleLeft, faHome, faHouseChimneyWindow, faCalendarDays, faComments, faIdCard, faCoins);

export default {
  name: "TheOwnerSidebar",
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