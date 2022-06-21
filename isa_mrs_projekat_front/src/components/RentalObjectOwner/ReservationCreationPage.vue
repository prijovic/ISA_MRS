<template>
  <div class="row">
    <div class="col-2"></div>
    <div class="col-8 pt-5 mb-5">
      <div class="container px-4 py-3 rounded form" spellcheck="false" >
        <div class="container-fluid">
          <h3>Create reservation</h3>
          <div class="row main justify-content-center">
            <div class="row main">
              <div class="col main">
                <div class="row">
                  <div class="col-3"></div>
                  <div class="col-6">
                    <div class="row justify-content-center mt-4">
                      <label class="ms-3">Client</label>
                    </div>

                    <div class="row justify-content-center mt-4">
                      <label class="ms-3">Init and term date</label>
                      <div class="container p-0 m-0 text-center" style="max-width: 400px">
                        <date-picker v-model="range" mode="dateTime" is-range is24hr :firstDayOfWeek=2>
                        </date-picker>
                      </div>
                    </div>

                    <div class="tags row justify-content-center mt-4">
                      <label class="ms-3">Equipments</label>
                      <div v-for="(tag, i) in equipments" :key="i" class="tagDiv chip chip-outline btn-outline-primary me-2 px-3 my-1" data-ripple-color="dark">
                        {{tag.name}}
                      </div>
                    </div>

                    <div class=" row justify-content-center p-0">
                      <label class="ms-3">Services</label>
                      <table class="mt-1 tag-container" id="servicesTable" style="max-width: 400px">
                        <thead v-if="services.length > 0" style="background-color: transparent">
                        <tr>
                          <th style="background-color: #008970; color: #f7f7f2; border-bottom: #008970 1px solid">Service</th>
                          <th style="background-color: #008970; color: #f7f7f2; border-left: #008970 1px solid">Price</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="(service, index) in services" :key="index" :class="index%2!==0?'odd':'even'">
                          <td style="border-right: #008970 1px solid;word-wrap: break-word;">
                            <div class="d-flex d-inline-flex justify-content-between p-0">
                              <div>{{service.name}}</div>
                            </div>
                          </td>
                          <td style="word-wrap: break-word;">
                            <div class="d-flex d-inline-flex justify-content-between p-0">
                              <div>{{service.price}}</div>
                            </div>
                          </td>
                        </tr>
                        </tbody>
                      </table>
                    </div>

                  </div>
                  <div class="col-3">
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="d-flex pt-3 justify-content-center">
            <router-link to="../" class="btn btn-red mt-3 me-1">Cancel</router-link>
            <button type="button" class="btn mt-3">Submit</button>
          </div>
        </div>
      </div>
    </div>
    <div class="col-2"></div>
  </div>
</template>

<script>
import {DatePicker} from "v-calendar"
import store from "@/store";
import axios from "axios";

export default {
  name: "ReservationCreationPage",
  components: {DatePicker},
  data() {
    return {
      clients: [],
      initDate: null,
      termDate: null,
      services: [],
      equipments: [],
      range:{
        start: null,
        end: null
      },
      serviceName: "",
      servicePrice: null
    }
  },
  mounted() {
    axios.get("/", {
      headers:{
        Authorization: "Bearer " + store.getters.access_token,
      },
    })
  }
}
</script>

<style scoped>
.tagDiv {
  width: fit-content;
  border-radius: 30px;
  border: 1px solid #008970;
  cursor: pointer;
  font-weight: 700;
  color: #008970;
}

.tagDiv:hover {
  background-color: #008970;
  color: white;
}

label {
  max-width: 400px;
}

h3 {
  text-align: center;
}
</style>