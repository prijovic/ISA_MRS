<template>
  <div class="sidebar rounded-right" :style="{width:sidebarWidth}">
    <MenuLink to="/admin" icon="dashboard">Dashboard</MenuLink>
    <MenuLink to="/admin/requests" icon="envelope">Requests</MenuLink>
    <MenuLink to="/admin/users" icon="users">Users</MenuLink>
    <MenuLink to="/admin/rentals" icon="boxes-packing">Rental Objects</MenuLink>
    <MenuLink to="/admin/reviews" icon="comments">Reviews</MenuLink>
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
import { faAngleDoubleLeft, faAward, faEnvelope, faBullhorn, faUsers, faCoins, faComments, faDashboard, faBoxesPacking} from "@fortawesome/free-solid-svg-icons";
import MenuLink from "@/components/GeneralComponents/MenuLink";

library.add(faAngleDoubleLeft, faAward, faEnvelope, faBullhorn, faUsers, faCoins, faComments, faDashboard, faBoxesPacking);

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

</style>