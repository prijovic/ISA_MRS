<template>
  <TheAdminNavbar></TheAdminNavbar>
  <TheAdminSidebar></TheAdminSidebar>
  <AdminContentPage :style="{'margin-left': sidebarWidth}"></AdminContentPage>
</template>

<script>
import TheAdminNavbar from "@/components/Administrator/AdminMainView/components/TheAdminNavbar/TheAdminNavbar";
import TheAdminSidebar from "@/components/Administrator/AdminMainView/components/TheAdminSidebar/TheAdminSidebar";
import AdminContentPage from "@/components/Administrator/AdminMainView/components/AdminContentPage/AdminContentPage";
import {sidebarWidth} from "@/components/Administrator/AdminMainView/components/TheAdminSidebar/state";

export default {
  name: "AdminMainView",
  components: {
    AdminContentPage,
    TheAdminNavbar,
    TheAdminSidebar
  },
  setup() {
    return {sidebarWidth};
  }
}
</script>

<style scoped>

</style>