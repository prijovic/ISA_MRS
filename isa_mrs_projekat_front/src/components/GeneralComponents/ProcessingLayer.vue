<template>
  <div class="processing p-0">
    <div class="loader-container p-0">
      <div class="loader p-0 mx-auto">
        <div class="ball"></div>
        <div class="ball"></div>
        <div class="ball"></div>
        <span>Processing...</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ProcessingLayer"
}
</script>

<style scoped>
  .processing {
    height: 100%;
    width: 100%;
    opacity: 0.8;
    background-color: #6c757d;
    z-index: 1000;
    position: absolute;
    display: table;
  }

  .loader-container {
    display: table-cell;
    vertical-align: middle;
  }

  .loader {
    width: 185px;
    height: 100px;
    display: flex;
    flex-wrap: wrap;
    align-items: flex-end;
    justify-content: space-between;
  }

  .loader span {
    font-size: 35px;
    text-transform: uppercase;
    margin: auto;
    font-weight: bold;
    text-shadow: #99EEDF 1px 1px;
  }

  .ball {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    background-color: #008970;
    border: 2px solid #99EEDF;
    animation: bounce .5s alternate infinite;
  }

  .ball:nth-child(2) {
    animation-delay: .16s;
  }

  .ball:nth-child(3) {
    animation-delay: .36s;
  }

  @keyframes bounce {
    from {
      transform: scaleX(1.25);
    }
    to {
      transform: translateY(-50px) scaleX(1);
    }
  }
</style>