<template>
  <div class="container-fluid" style="background-color: white;">
    <div class="row ps-2 pt-3 main d-flex justify-content-center">
      <div class="col-xl-4 col-lg-5 col-md-7 col-sm-9 col-xs-12 mb-3 main" >
        <div style="position: sticky; top: 2%;">
          <div class="row main">
            <div class="profilePic">
              <ThePhoto v-if="instructor.photo" class="card-img-top" :photo="instructor.photo" alt=""/>
            </div>
          </div>
          <div class="row main" style="text-align: center;">
            <p class="display-5"><i><strong>{{ fullName }}</strong></i></p>
          </div>
          <div class="row ps-2 mt-4 main" >
            <div style="display: flex;">
              <p class="h3 me-3" style="font-weight: 100;">E-mail:</p>
              <p class="h3">{{ this.instructor.email }}</p>
            </div>
          </div>
          <div class="row ps-2 main">
            <div style="display: flex;">
              <p class="h3 me-3" style="font-weight: 100;">Phone:</p>
              <p class="h3">{{ this.instructor.phone }}</p>
            </div>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<script>
import ThePhoto from "@/components/GeneralComponents/ThePhoto";

export default {
  name: "InstructorProfile",
  components: {
    ThePhoto,
  },
  props: ["instructor"],
  data() {
    return {
      instr: null,
    }
  },
  computed: {
    fullName() {
      return this.instructor.name + " " + this.instructor.surname;
    },
  }
}
</script>

<style scoped>

</style>