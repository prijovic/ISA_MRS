<template>
  <div class="sidebar rounded-right" :style="{width:sidebarWidth}">
    <MenuLink to="/client/profile" icon="id-card">Profile</MenuLink>
    <hr>
    <MenuLink to="/client/VacationRental/all" icon="house">Rental Houses</MenuLink>
    <MenuLink to="/client/Boat/all" icon="ship">Boats</MenuLink>
    <MenuLink to="/client/Adventure/all" icon="mountain-sun">Adventures</MenuLink>
    <MenuLink to="/client/Instructors" icon="fish-fins">Fishing Instructors</MenuLink>
    <hr>
    <MenuLink to="/client/ReservationsHistory" icon="address-book">Reservation History</MenuLink>
    <span class="collapse-icon" :class="{'rotate-180': collapsed}" @click="toggleSidebar">
      <font-awesome-icon icon="angle-double-left"></font-awesome-icon>
    </span>
  </div>
</template>

<script>
import {collapsed, toggleSidebar, sidebarWidth} from "@/components/state";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faAngleDoubleLeft, faHouse, faShip, faMountainSun, faIdCard, faAddressBook, faFishFins} from "@fortawesome/free-solid-svg-icons";
import MenuLink from "@/components/GeneralComponents/MenuLink";
library.add(faAngleDoubleLeft, faHouse, faShip, faMountainSun, faIdCard, faAddressBook, faFishFins);
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

</style>