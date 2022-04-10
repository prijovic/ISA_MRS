<template>
  <TheBoatOwnerNavbar></TheBoatOwnerNavbar>
  <TheBoatOwnerSidebar></TheBoatOwnerSidebar>
  <BoatOwnerContentPage :style="{'margin-left': sidebarWidth}"></BoatOwnerContentPage>
</template>

<script>
import TheBoatOwnerNavbar from "@/components/BoatOwner/BoatOwnerMainView/components/TheBoatOwnerNavbar/TheBoatOwnerNavbar";
import TheBoatOwnerSidebar from "@/components/BoatOwner/BoatOwnerMainView/components/TheBoatOwnerSidebar/TheBoatOwnerSidebar";
import BoatOwnerContentPage from "@/components/BoatOwner/BoatOwnerMainView/components/BoatOwnerContentPage/BoatOwnerContentPage";
import {sidebarWidth} from "@/components/BoatOwner/BoatOwnerMainView/components/TheBoatOwnerSidebar/state";

export default {
  name: "BoatOwnerMainView",
  components: {
    BoatOwnerContentPage,
    TheBoatOwnerSidebar,
    TheBoatOwnerNavbar
  },
  setup(){
    return {sidebarWidth};
  }
}
</script>

<style scoped>

</style>