package com.otchi.api.facades.dto;

import com.otchi.domaine.kitchen.models.Instruction;


public class InstructionDTO implements DTO<Instruction> {

    private Long id;
    private String content;

    InstructionDTO(Instruction instruction) {
        extractFromDomain(instruction);
    }

    @Override
    public Instruction toDomain() {
        return new Instruction(content);
    }

    @Override
    public void extractFromDomain(Instruction instruction) {
        this.id = instruction.getId();
        this.content = instruction.getContent();
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
