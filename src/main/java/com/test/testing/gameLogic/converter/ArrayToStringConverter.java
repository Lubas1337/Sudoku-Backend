package com.test.testing.gameLogic.converter;

import com.test.testing.gameLogic.util.SudokuUtil;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ArrayToStringConverter implements AttributeConverter<int[], String> {
    @Override
    public String convertToDatabaseColumn(int[] grid) {
        return SudokuUtil.convertToString(grid);
    }

    @Override
    public int[] convertToEntityAttribute(String dbData) {
        return SudokuUtil.convertToArray(dbData);
    }
}