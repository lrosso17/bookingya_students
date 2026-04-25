import { defineConfig } from '@playwright/test';

export default defineConfig({
  testDir: './tests',
  use: {
    baseURL: 'http://localhost:8080/api',
    extraHTTPHeaders: {
      'Content-Type': 'application/json'
    }
  }
});