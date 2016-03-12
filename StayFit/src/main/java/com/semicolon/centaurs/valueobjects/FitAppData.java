package com.semicolon.centaurs.valueobjects;

public class FitAppData
{
    private String startDate;

    private String dataType;

    private String endDate;

    private FieldValues fieldValues;

    public String getStartDate ()
    {
        return startDate;
    }

    public void setStartDate (String startDate)
    {
        this.startDate = startDate;
    }

    public String getDataType ()
    {
        return dataType;
    }

    public void setDataType (String dataType)
    {
        this.dataType = dataType;
    }

    public String getEndDate ()
    {
        return endDate;
    }

    public void setEndDate (String endDate)
    {
        this.endDate = endDate;
    }

    public FieldValues getFieldValues ()
    {
        return fieldValues;
    }

    public void setFieldValues (FieldValues fieldValues)
    {
        this.fieldValues = fieldValues;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [startDate = "+startDate+", dataType = "+dataType+", endDate = "+endDate+", fieldValues = "+fieldValues+"]";
    }
}
			
			