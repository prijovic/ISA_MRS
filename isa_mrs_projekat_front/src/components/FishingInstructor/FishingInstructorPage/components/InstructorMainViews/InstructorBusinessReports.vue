<template>
  <div class="row">
    <div class="col-1"></div>
    <div class="col-10">
      <div class="row justify-content-between mt-5">
        <table>
          <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th class="text-end pe-5">Grade</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(grade, index) in this.grades" :key="index" :class="index%2!==0?'odd':'even'">
            <td>{{grade.rentalObject.id}}</td>
            <td>{{grade.rentalObject.name}}</td>
            <td class="text-end pe-5">{{grade.grade==="0"?"/":grade.grade}}</td>
          </tr>
          <tr style="background-color: #6c757d; color: white">
            <td class="fw-bolder text-start">Average:</td>
            <td colspan="3" class="fw-bolder text-end pe-5">{{averageGrade.toFixed(1)}}</td>
          </tr>
          </tbody>
        </table>
      </div>
      <div class="row">
        <div class="container d-flex justify-content-center p-3 rounded shadow mt-5 mb-5">
          <Line :chart-data="chartData1" :key="chartData1.datasets[0].data"></Line>
        </div>
      </div>
      <div class="row">
        <div class="container d-flex justify-content-center p-3 rounded shadow mt-5 mb-5">
          <Line :chart-data="chartData2" :key="chartData2.datasets[0].data"></Line>
        </div>
      </div>
      <div class="row">
        <div class="container d-flex justify-content-center p-3 rounded shadow mt-5 mb-5">
          <Line :chart-data="chartData3" :key="chartData3.datasets[0].data"></Line>
        </div>
      </div>
      <div class="row container-fluid pe-0 ps-0 me-0 ms-0 mb-5 mt-5">
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
    </div>
    <div class="col-1"></div>
  </div>
</template>

<script>
import {DatePicker} from "v-calendar";
import { Line } from 'vue-chartjs';
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale, LineElement, PointElement, LineController } from 'chart.js';
import {useStore} from "vuex";
import axios from "axios";
import {toggleLoading} from "@/components/state";
import store from "@/store";

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale, LineElement, PointElement, LineController)

export default {
  name: "InstructorBusinessReports",
  components: {DatePicker, Line},
  data() {
    return {
      incomes: [],
      range: {
        start: new Date(new Date().setFullYear(new Date().getFullYear() - 1)),
        end: new Date()
      },
      grades: [],
      chartData1: {
        labels: [],
        datasets: [
          {
            label: 'Reservations last year',
            backgroundColor: '#4650dd',
            borderColor: 'rgb(70, 80, 221, 0.4)',
            data: []
          }
        ]
      },
      chartData2: {
        labels: [],
        datasets: [
          {
            label: 'Reservations last month',
            backgroundColor: '#4650dd',
            borderColor: 'rgb(70, 80, 221, 0.4)',
            data: []
          }
        ]
      },
      chartData3: {
        labels: [],
        datasets: [
          {
            label: 'Reservations last week',
            backgroundColor: '#4650dd',
            borderColor: 'rgb(70, 80, 221, 0.4)',
            data: []
          }
        ]
      }
    }
  },
  mounted() {
    toggleLoading();
    const store = useStore();
    axios.get("/Profits/instructorDashboard", {headers: {
        Authorization: "Bearer " + store.getters.access_token,
      }
    })
    .then((response) => {
      const dashboardData = response.data;
      this.grades = dashboardData.rentalGrades;
      const nodes1 = dashboardData.yearlyGraph.nodes;
      const nodes2 = dashboardData.monthlyGraph.nodes;
      const nodes3 = dashboardData.weeklyGraph.nodes;
      let dataset1 = [];
      let dataset2 = [];
      let dataset3 = [];
      for (let i = 0; i < nodes1.length; i++) {
        this.chartData1.labels.push(nodes1[i].month);
        dataset1.push(nodes1[i].value);
      }
      for (let i = 0; i < nodes2.length; i++) {
        this.chartData2.labels.push(nodes2[i].month);
        dataset2.push(nodes2[i].value);
      }
      for (let i = 0; i < nodes3.length; i++) {
        this.chartData3.labels.push(nodes3[i].month);
        dataset3.push(nodes3[i].value);
      }
      this.chartData1.datasets[0].data = dataset1;
      this.chartData2.datasets[0].data = dataset2;
      this.chartData3.datasets[0].data = dataset3;
      this.generateReport();
      toggleLoading();
    })
    .catch((e) => {
      console.log(e);
      this.$notify({
        title: "Server error",
        text: "Server is currently off. Please try again later...",
        type: "error"
      });
      toggleLoading();
    })
  },
  methods: {
    generateReport() {
      toggleLoading();
      const start = this.range.start.toISOString().slice(0, 19);
      const end = this.range.end.toISOString().slice(0, 19);
      axios.get("/Profits/instructorReport", {headers: {
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
  computed: {
    averageGrade() {
      let gradeTotal = 0;
      let count = 0;
      this.grades.forEach(grade => {
        if (grade !== "0") {
          count += 1;
          gradeTotal += parseFloat(grade.grade);
        }
      })
      return count===0?count:gradeTotal/count;
    },
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