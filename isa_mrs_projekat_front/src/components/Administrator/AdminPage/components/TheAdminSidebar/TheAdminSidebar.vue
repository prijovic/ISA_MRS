<template>
  <div class="sidebar rounded-right" :style="{width:sidebarWidth}">
    <MenuLink to="/#" icon="dashboard">Dashboard</MenuLink>
    <MenuLink to="/admin/requests" icon="envelope">Requests</MenuLink>
    <MenuLink to="/#" icon="users">Users</MenuLink>
    <MenuLink to="/#" icon="mountain-sun">Adventures</MenuLink>
    <MenuLink to="/#" icon="ship">Boats</MenuLink>
    <MenuLink to="/#" icon="house">Rental Houses</MenuLink>
    <MenuLink to="/#" icon="comments">Reviews</MenuLink>
    <MenuLink to="/#" icon="bullhorn">Reports</MenuLink>
    <MenuLink to="/#" icon="coins">Finances</MenuLink>
    <MenuLink to="/#" icon="award">Loyalty Program</MenuLink>
    <span class="collapse-icon" :class="{'rotate-180': collapsed}" @click="toggleSidebar">
      <font-awesome-icon icon="angle-double-left"></font-awesome-icon>
    </span>
  </div>
</template>

<script>
import {collapsed, toggleSidebar, sidebarWidth} from "@/components/state";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faAngleDoubleLeft, faAward, faEnvelope, faBullhorn, faHouse, faUsers, faShip, faMountainSun, faCoins, faComments, faDashboard} from "@fortawesome/free-solid-svg-icons";
import MenuLink from "@/components/GeneralComponents/MenuLink";

library.add(faAngleDoubleLeft, faAward, faEnvelope, faBullhorn, faHouse, faUsers, faShip, faMountainSun, faCoins, faComments, faDashboard);

export default {
  name: "TheAdminSidebar",
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