<template>
  <div class="gradient-progress" :style="progressStyle">
    <div class="progress-bar" :style="barStyle"></div>
    <span v-if="value > 0" class="progress-value" :style="valueStyle">{{ value }}%</span>
  </div>
</template>

<script>
export default {
  name: 'GradientProgress',
  props: {
    value: {
      type: Number,
      default: 0
    },
    max: {
      type: Number,
      default: 100
    },
    colors: {
      type: Array,
      default() {
        return ['#FF3333', '#FF9933', '#FFFF33', '#33FF33', '#3366FF'];
      }
    }
  },
  computed: {
    progressStyle() {
      const width = `${this.value / this.max * 100}%`;
      return { width };
    },
    barStyle() {
      let gradient = `linear-gradient(to right, ${this.colors.join(',')})`;
      return { background: gradient };
    },
    valueStyle() {
      const left = `${this.value / this.max * 100}%`;
      return { left };
    }
  }
};
</script>

<style scoped>
.gradient-progress {
  width: 100%;
  height: 80px;
  border: 1px solid #ccc;
  position: relative;
}

.progress-bar {
  height: 100%;
  transition: width 0.3s ease-in-out;
}

.progress-value {
  position: absolute;
  top: -8px;
  font-size: 12px;
  color: white;
  padding: 2px 4px;
  background-color: rgba(0, 0, 0, 0.7);
  border-radius: 4px;
}
</style>
