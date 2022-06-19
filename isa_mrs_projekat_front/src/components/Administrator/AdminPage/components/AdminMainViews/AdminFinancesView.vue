<template>
  <div class="row">
    <div class="col-1"></div>
    <div class="col-10 pt-5 mb-5">
      <div class="row container-fluid pe-0 ps-0 me-0 ms-0">
        <div class="col-4 text-center">
          <date-picker mode="range" v-model="range" is-range></date-picker>
          <button class="btn mt-2" @click="generateReport">Generate Report</button>
        </div>
        <div class="col-8">
          <div class="align-items-center">
            <div class="row main-col text-center header rounded mb-3">
              <table>
                <thead>
                <tr>
                  <th>ID</th>
                  <th>Type</th>
                  <th>Time Stamp</th>
                  <th class="text-end pe-5">Value</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(income, index) in this.incomes" :key="index" :class="index%2!==0?'odd':'even'">
                  <td>{{income.id}}</td>
                  <td>{{income.type}}</td>
                  <td>{{income.timeStamp.replace('T', ' ')}}</td>
                  <td class="text-end pe-5">{{income.value}}$</td>
                </tr>
                <tr style="background-color: #6c757d; color: white">
                  <td class="fw-bolder text-start">Total:</td>
                  <td colspan="3" class="fw-bolder text-end pe-5">{{totalIncome.toFixed(1)}}$</td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
      <div class="col-1"></div>
    </div>
  </div>
</template>

<script>
import {DatePicker} from "v-calendar";
import {toggleLoading} from "@/components/state";
import axios from "axios";
import store from "@/store";

export default {
  name: "AdminFinancesView",
  components: {DatePicker},
  data() {
    return {
      incomes: [],
      range: {
        start: new Date(new Date().setFullYear(new Date().getFullYear() - 1)),
        end: new Date()
      }
    }
  },
  methods: {
    generateReport() {
      toggleLoading();
      const start = this.range.start.toISOString().slice(0, 19);
      const end = this.range.end.toISOString().slice(0, 19);
      console.log(start);
      console.log(end);
      axios.get("/Profits/adminReport", {headers: {
          Authorization: "Bearer " + store.getters.access_token,
        },
        params: {
          start: start,
          end: end
        }
      })
      .then((response) => {
        this.incomes = response.data;
        toggleLoading();
      })
      .catch(() => {
        this.$notify({
          title: "Server error",
          text: "Server is currently off. Please try again later...",
          type: "error"
        });
        toggleLoading();
      });
    }
  },
  mounted() {
    this.generateReport();
  },
  computed: {
    totalIncome() {
      let income = 0;
      this.incomes.forEach(i => {
        income += i.value;
      })
      return income;
    }
  }
}
</script>

<style scoped>

</style>