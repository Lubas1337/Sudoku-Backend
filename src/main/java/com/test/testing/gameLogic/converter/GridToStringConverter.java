package com.test.testing.gameLogic.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class GridToStringConverter implements AttributeConverter<int[][], String> {

    @Override
    public String convertToDatabaseColumn(int[][] grid) {
        StringBuilder builder = new StringBuilder();

        for (int[] row : grid)
            for (int data : row)
                builder.append(data);

        return builder.toString();
    }

    @Override
    public int[][] convertToEntityAttribute(String dbData) {
        final int length = 9;
        int[][] grid = new int[length][length];

        for (int row = 0; row < length; row++) {
            for (int column = 0; column < length; column++) {
                int index = row * length + column;
                grid[row][column] = dbData.charAt(index) - '0';
            }
        }
        return grid;
    }
}
