<template>
  <TheOwnerNavbar></TheOwnerNavbar>
  <TheOwnerSidebar></TheOwnerSidebar>
  <OwnerContentPage :style="{'margin-left': sidebarWidth}"></OwnerContentPage>
</template>

<script>
import TheOwnerNavbar from "@/components/VacationRentalOwner/OwnerMainView/components/TheOwnerNavbar/TheOwnerNavbar";
import TheOwnerSidebar from "@/components/VacationRentalOwner/OwnerMainView/components/TheOwnerSidebar/TheOwnerSidebar";
import OwnerContentPage from "@/components/VacationRentalOwner/OwnerMainView/components/OwnerContentPage/OwnerContentPage";
import {sidebarWidth} from "@/components/VacationRentalOwner/OwnerMainView/components/TheOwnerSidebar/state";

export default {
  name: "OwnerMainView",
  components: {
    OwnerContentPage,
    TheOwnerNavbar,
    TheOwnerSidebar
  },
  setup() {
    return {sidebarWidth};
  }
}
</script>

<style scoped>

</style>